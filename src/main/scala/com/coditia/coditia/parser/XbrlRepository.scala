/*
 * XBRL repository needed to use XBRLAPI.
 *
 * Copyright (C) 2014 Jorge Perez Burgos <jorge.perez*at*coditia.com>.
 *
 * This work is licensed under the terms of the Affero GNU GPL, version 3.
 * See the LICENSE file in the top-level directory.
 *
 */
package com.coditia.coditia.parser

import scala.collection.JavaConversions._

import java.net.{URI, URISyntaxException}
import java.io.File

import net.liftweb.common.Loggable
import net.liftweb.util.Props

import org.xmldb.api.base.Collection
import org.xmldb.api.base.XMLDBException
import org.exist.xmldb.DatabaseInstanceManager

import org.xbrlapi.{Instance, Schema}
import org.xbrlapi.cache.{Cache, CacheImpl}
import org.xbrlapi.data.Store
import org.xbrlapi.data.resource.InStoreMatcherImpl
import org.xbrlapi.networks.{Network, Networks, Storer, StorerImpl}
import org.xbrlapi.loader.{Loader, LoaderImpl}
import org.xbrlapi.sax.{EntityResolver, EntityResolverImpl}
import org.xbrlapi.utilities.XBRLException
import org.xbrlapi.xlink.{XLinkProcessor,XLinkProcessorImpl}
import org.xbrlapi.xlink.handler.{XBRLCustomLinkRecogniserImpl, XBRLXLinkHandlerImpl}

/**
 * Class XBRL repository manager
 */
class XbrlRepository extends Loggable {

  private val cacheFile: File = new File(Props.get("coditia.xmlcachepath")
      openOrThrowException("Unable to open cache xml repository"))

  private val cache: Cache = new CacheImpl(cacheFile)

  val store = instantiateStore(Props.get("coditia.xmldbstore")
      openOrThrowException("No xml database specified"))

  private val loader = createLoader
  val storer: Storer = new StorerImpl(store)

  def getSchema(instance: Instance): Schema = store.getFragmentsFromDocument(
        instance.getSchemaRefs.get(0).getAbsoluteHref, "Schema").get(0)

  def getInstance(docUri: String): Instance = {
    require(docUri != null && docUri != "")
    val targetUri = new URI(docUri)
    def discoverUri = {
      require(targetUri != null && targetUri != "")
      logger.info("Discovering documents for: " + targetUri)
      loader.stashURI(targetUri)
      val size = store.getSize
      loader.discover(targetUri)

      // Check that all documents were loaded OK.
      val stubs = store.getStubs
      if (!stubs.isEmpty) {
        for (stub <- stubs) {
          logger.error("Unable to load document " + stub.getResourceURI + ": " + stub.getReason)
        }
        throw new IllegalStateException("Some xbrl documents were not loaded.")
      }

      // Delete prohibited or overridden relationships from the persisted relationships in the data store
      if (store.getSize() > size) {
        storer.deleteInactiveRelationships
      }
    }

    discoverUri

    logger.info("Getting SEC Xbrl instance...")
    val instances = store.getFragmentsFromDocument(targetUri, "Instance")
    if (instances.size() > 1) {
      store.close
      throw new XBRLException(
      "There are duplicates of the target instance in the data store.  The store appears to be corrupted.")
    }

    if (instances.size() == 0) {
      store.close
      throw new XBRLException("The target document '" + targetUri + "' is not an XBRL instance.")
    }

    instances.get(0)
  }

  private def createLoader: Loader = {
    val xlinkHandler: XBRLXLinkHandlerImpl = new XBRLXLinkHandlerImpl()
    val clr: XBRLCustomLinkRecogniserImpl  = new XBRLCustomLinkRecogniserImpl()
    val xlinkProcessor: XLinkProcessor     = new XLinkProcessorImpl(xlinkHandler ,clr)

    // Rivet errors in the SEC XBRL data require these URI remappings to
    // prevent discovery process from breaking.
    val map = new java.util.HashMap[URI,URI]()
    map.put(URI.create("http://www.xbrl.org/2003/linkbase/xbrl-instance-2003-12-31.xsd"),
        URI.create("http://www.xbrl.org/2003/xbrl-instance-2003-12-31.xsd"))
    map.put(URI.create("http://www.xbrl.org/2003/instance/xbrl-instance-2003-12-31.xsd"),
        URI.create("http://www.xbrl.org/2003/xbrl-instance-2003-12-31.xsd"))
    map.put(URI.create("http://www.xbrl.org/2003/linkbase/xbrl-linkbase-2003-12-31.xsd"),
        URI.create("http://www.xbrl.org/2003/xbrl-linkbase-2003-12-31.xsd"))
    map.put(URI.create("http://www.xbrl.org/2003/instance/xbrl-linkbase-2003-12-31.xsd"),
        URI.create("http://www.xbrl.org/2003/xbrl-linkbase-2003-12-31.xsd"))
    map.put(URI.create("http://www.xbrl.org/2003/instance/xl-2003-12-31.xsd"),
        URI.create("http://www.xbrl.org/2003/xl-2003-12-31.xsd"))
    map.put(URI.create("http://www.xbrl.org/2003/linkbase/xl-2003-12-31.xsd"),
        URI.create("http://www.xbrl.org/2003/xl-2003-12-31.xsd"))
    map.put(URI.create("http://www.xbrl.org/2003/instance/xlink-2003-12-31.xsd"),
        URI.create("http://www.xbrl.org/2003/xlink-2003-12-31.xsd"))
    map.put(URI.create("http://www.xbrl.org/2003/linkbase/xlink-2003-12-31.xsd"),
        URI.create("http://www.xbrl.org/2003/xlink-2003-12-31.xsd"))

    val entityResolver: EntityResolver     = new EntityResolverImpl(cacheFile, map)
    val loader: Loader                     = new LoaderImpl(store,xlinkProcessor, entityResolver)

    loader.setCache(cache)
    loader.setEntityResolver(entityResolver)
    xlinkHandler.setLoader(loader)

    loader
  }

  private def instantiateStore(dbStore: String): Store = {
    logger.info("Trying to instantiate XML DB store " + dbStore)
    val store = dbStore match {
      case "eXist" =>
                   val dbPath    = Props.get("coditia.eXist.xmldbpath") openOr "/"
                   val container = Props.get("coditia.eXist.container") openOr "default"
                   val hostdb    = Props.get("coditia.eXist.hostdb") openOr "localhost"
                   val portdb    = Props.get("coditia.eXist.portdb") openOr "8080"
                   val userdb    = Props.get("coditia.eXist.userdb") openOr "admin"
                   val passdb    = Props.get("coditia.eXist.passdb") openOr ""
                   val namedb    = Props.get("coditia.eXist.namedb") openOr "exist/xmlrpc/db"
                   new org.xbrlapi.data.exist.StoreImpl(hostdb, portdb, namedb, userdb, passdb, dbPath, container)

      case "eXistEmbedded" =>
                   val dbPath    = Props.get("coditia.eXistEmbedded.xmldbpath") openOr "/"
                   val container = Props.get("coditia.eXistEmbedded.container") openOr "default"
                   val userdb    = Props.get("coditia.eXistEmbedded.userdb") openOr "admin"
                   val passdb    = Props.get("coditia.eXistEmbedded.passdb") openOr ""
                   val namedb    = Props.get("coditia.eXistEmbedded.namedb") openOr "/db"
                   new org.xbrlapi.data.exist.embedded.StoreImpl(namedb, userdb, passdb, dbPath, container)

      case "dbxml" =>
                   val dbPath    = Props.get("coditia.dbxml.xmldbpath").
                                     openOrThrowException("No XML database path found")

                   val container = Props.get("coditia.dbxml.container") openOr "default"
                   new org.xbrlapi.data.bdbxml.StoreImpl(dbPath, container)

      case _ =>
        throw new IllegalStateException("Unable to initialize xbrlapi XML database")
    }

    store.setMatcher(new InStoreMatcherImpl(store, cache))

    store
  }
}

