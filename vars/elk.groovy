@GrabExclude(group='org.codehaus.groovy', module='groovy-all')
@Grab('org.elasticsearch:elasticsearch-groovy:2.1.2')
import org.elasticsearch.*
def call() {
  echo 'Hello...we imported elastic searc'
}