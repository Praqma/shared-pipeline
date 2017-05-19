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
   credentialsId: ''],
   changelog: false)

stageWithCheckPoint(" ") {
    echo "User decided to run me"
}
```
