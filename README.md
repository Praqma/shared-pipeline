# shared-pipeline
Repository with shared global pipeline libs. Usable within sandboxed objects

## Guide

https://jenkins.io/doc/book/pipeline/shared-libraries/

## Content

### stageWithCheckPoint

It is a stage wrapper that will be executed as a usual stage when triggered by webhook, polling or gerrit trigger. However, when it is retriggered by user it will offer to skip stages so you can fast-forward to pipeline steps and start running pipeline from the moment you want. If no answer given then it will continue automatically (default timeout is set to 5 min)

```
library identifier: 'praqma-shared@master', retriever: modernSCM(
  [$class: 'GitSCMSource',
   remote: 'https://github.com/Praqma/shared-pipeline.git',
   credentialsId: ''])

stageWithCheckPoint("ConditionalEcho") {
    echo "User decided to run me"
}
```

##### Stage wrappers and declarative pipeline

Declarative pipeline is more limited than scripted. It only accepts `stage` at the top level, so a wrapper like `stageWithCheckpoint` will not work. To use it with declarative, you will have to call it as a step:

```
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
