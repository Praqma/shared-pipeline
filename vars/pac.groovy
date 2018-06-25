def call(args = [:]) {
    def curDir = pwd()
    def minFile = "minimal_settings.yml"
    def latestTag = args.latestTag ?: "*"
    def settingsFile = args.settingsFile ?: minFile
    def pacArgs = args.pacArgs ?: ""

    docker.image("praqma/pac:3.0.0-12").inside() {
        if(!args.settingsFile) {
            sh "curl 'https://raw.githubusercontent.com/Praqma/Praqmatic-Automated-Changelog/master/settings/minimal_settings.yml -O'"
        }
        sh "pac from-latest-tag \"*\" --settings=$settingsFile $pacArgs -v"
    }
}