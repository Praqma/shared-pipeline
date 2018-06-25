def call(args = [:]) {

    def minFile = "minimal_settings.yml"
    def latestTag = args.latestTag ?: "*"
    def settingsFile = args.settingsFile ?: minFile
    def pacArgs = args.pacArgs ?: ""

    if(!args.settingsFile) {
        new File(minFile) << new URL("https://raw.githubusercontent.com/Praqma/Praqmatic-Automated-Changelog/master/settings/minimal_settings.yml").getText()
    }  

    docker.image("praqma/pac").inside() {
        sh "from-latest-tag \"$latestTag\" --settings=$settingsFile $pacArgs"
    }
}