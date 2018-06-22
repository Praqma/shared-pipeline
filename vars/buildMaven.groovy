def call(args = [:]) {
    docker.image(args.buildImage ?: "maven:3.5.3-jdk-8").inside("-v maven-repo:/root/.m2") {
        sh args.buildCommand ?: "mvn clean package"
    }    
}