package etc

import com.google.inject.{Inject, Singleton}
import play.api.libs.concurrent.CustomExecutionContext

@Singleton
class DefaultDbExecutionContext @Inject() (actorSystem: akka.actor.ActorSystem)
    extends CustomExecutionContext(actorSystem, "contexts.default-db-context")
