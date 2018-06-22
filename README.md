# shared-pipeline

Repository with shared global pipeline libs. Usable within sandboxed objects

## Guide

https://jenkins.io/doc/book/pipeline/shared-libraries/

## Content

### buildMaven

Command to build a maven project inside a maven container using the sidecar approach in Jenkins pipeline.

```groovy
docker.image(inputs.buildImage ?: "maven:3.5.3-jdk-8").inside("-v maven-repo:/root/.m2") {
    sh inputs.buildCommand ?: "mvn clean package"
}
```

#### Arguments.buildMaven

Arguments are passed in as named arguments. So for instance call it like so: `buildMaven(buildCommand: '...', buildImage: '...')`

- `buildCommand` The maven command to use. [Default: 'mvn clean package']
- `buildImage` The image to use in the sidecar. [Default: 'maven:3.5.3-jdk-8']

### releaseMaven

Command that releases a plugin to the target configured in the distribution management section of the `.pom` file.

```groovy
    sh 'curl https://raw.githubusercontent.com/Praqma/maven-info/master/settings.xml -O'
    withCredentials([usernamePassword(credentialsId: args.credentials ?: 'github', passwordVariable: 'passRelease', usernameVariable: 'userRelease'), string(credentialsId: 'jenkins-artifactory', variable: 'RELEASE_PW')]) {
        docker.image(args.buildImage ?: "maven:3.5.3-jdk-8").inside("-v maven-repo:/root/.m2") {
            sh 'git config user.email "release@praqma.net" && git config user.name "Praqma Release User"'
            sh 'mvn clean release:prepare release:perform -B -s settings.xml -Dusername=$userRelease -Dpassword=$passRelease'
        }
    }
```

#### Arguments.releaseMaven

Arguments are passed in as named arguments. So for instance call it like so: `releaseMaven(credentials: '...', buildImage: '...')`

- `buildImage` The image to use in the sidecar. [Default: 'maven:3.5.3-jdk-8']
- `credentials` The credential id to use. We require that a username/password credential is used. [Default: 'github']


### stageWithCheckPoint

It is a stage wrapper that will be executed as a usual stage when triggered by webhook, polling or gerrit trigger. However, when it is retriggered by user it will offer to skip stages so you can fast-forward to pipeline steps and start running pipeline from the moment you want. If no answer given then it will continue automatically (default timeout is set to 5 min)

```groovy
library identifier: 'praqma-shared@master', retriever: modernSCM(
  [$class: 'GitSCMSource',
   remote: 'https://github.com/Praqma/shared-pipeline.git',
   credentialsId: ''])

stageWithCheckPoint("ConditionalEcho") {
    echo "User decided to run me"
}
```

### pac

#### Arguments.pac

#### Stage wrappers and declarative pipeline

Declarative pipeline is more limited than scripted. It only accepts `stage` at the top level, so a wrapper like `stageWithCheckpoint` will not work. To use it with declarative, you will have to call it as a step:

```groovy
@Library('praqma-shared') _
pipeline {
    agent any
    stages {
        stage('Wrapper stage') {
            steps {
                stageWithCheckPoint('Custom stage') {
                    steps {
                        echo 'This seems a bit excessive'
                    }
                }
            }
        }
    }
}
```

So far this is the only method we have found of making it work, and it is still limited. We recommend using scripted pipeline if you need to use advanced functionality like stage wrappers.
