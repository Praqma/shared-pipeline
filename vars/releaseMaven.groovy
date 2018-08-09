def call(args = [:]) {
    sh 'curl https://raw.githubusercontent.com/Praqma/maven-info/master/settings.xml -O'
    def credentialsToUse = args.credentials ?: 'github'
    def artifactoryUploader = args.artifactory ?: 'jenkins-artifactory'
    withCredentials([usernamePassword(credentialsId: credentialsToUse, passwordVariable: 'passRelease', usernameVariable: 'userRelease'), string(credentialsId: artifactoryUploader, variable: 'RELEASE_PW')]) {
        docker.image(args.buildImage ?: "maven:3.5.3-jdk-8").inside("-v maven-repo:/root/.m2") {
            if(!args.dryRun) {
                sh 'git config user.email "release@praqma.net" && git config user.name "Praqma Release User"'
                sh 'mvn clean release:prepare release:perform -B -s settings.xml -Dusername=$userRelease -Dpassword=$passRelease'
            } else {
                sh 'ls -al'
                sh 'env'
                echo "Using credentials with id $credentialsToUse for github access"
                echo "Using credentials with id $artifactoryUploader for artifactory upload"
                sh 'cat settings.xml'
            }
        }            
    }    
}
