import sbt.{AutoPlugin, PluginTrigger}

object GithubPlugin extends AutoPlugin {

  override def allRequirements: PluginTrigger = super.allRequirements

  override def trigger: PluginTrigger = super.trigger
}
