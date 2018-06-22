def call(args = [:]) {

    def latestTag = args.latestTag ?: "*"
    def settingsFile = args.settingsFile ?: "default_settings.yml"
    def pacArgs = args.pacArgs ?: ""  

    docker.image("praqma/pac").inside() {
        sh "from-latest-tag \"$latestTag\" --settings=$settingsFile $pacArgs"
    }
}