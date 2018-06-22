def call(inputs = [:]) {
    docker.image(inputs.buildImage ?: "maven:3.5.3-jdk-8").inside("-v maven-repo:/root/.m2") {
        sh inputs.buildCommand ?: "mvn clean package"
    }    
}