package services

import dao.RequestMetadataDao
import models.RequestMetadata

import java.util.UUID
import scala.concurrent.Future

trait RequestMetadataService {
  def insert(requestMeta: RequestMetadata): Future[UUID]
}

class RequestMetadataServiceImpl(dao: RequestMetadataDao) extends RequestMetadataService {
  override def insert(requestMeta: RequestMetadata): Future[UUID] =
    dao.insert(requestMeta)
}
