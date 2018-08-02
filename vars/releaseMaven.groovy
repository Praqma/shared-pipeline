def call(args = [:]) {
    sh 'curl https://raw.githubusercontent.com/Praqma/maven-info/master/settings.xml -O'
    withCredentials([usernamePassword(credentialsId: args.credentials ?: 'github', passwordVariable: 'passRelease', usernameVariable: 'userRelease'), string(credentialsId: 'jenkins-artifactory', variable: 'RELEASE_PW')]) {
        docker.image(args.buildImage ?: "maven:3.5.3-jdk-8").inside("-v maven-repo:/root/.m2") {
            sh 'git config user.email "release@praqma.net" && git config user.name "Praqma Release User"'
            sh 'mvn clean release:prepare release:perform -B -s settings.xml -Dusername=$userRelease -Dpassword=$passRelease'
        }            
    }    
}
