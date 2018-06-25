def call(args = [:]) {
    def curDir = pwd()
    def minFile = "${curDir}/minimal_settings.yml"
    def latestTag = args.latestTag ?: "*"
    def settingsFile = args.settingsFile ?: minFile
    def pacArgs = args.pacArgs ?: ""

    if(!args.settingsFile) {
        echo "Writing minimal settings file..."
        new File(curDir, minFile) << new URL("https://raw.githubusercontent.com/Praqma/Praqmatic-Automated-Changelog/master/settings/minimal_settings.yml").getText()
        echo "Done writing minimal settings file..."
    }          
    docker.image("praqma/pac").inside() {
        sh "from-latest-tag \"$latestTag\" --settings=$settingsFile $pacArgs"
    }
}