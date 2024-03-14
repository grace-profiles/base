import javax.management.remote.JMXServiceURL
import javax.management.remote.JMXConnectorFactory
import javax.management.ObjectName
import org.grails.io.support.*

description("Stops the running Grace application") {
    usage "grace stop-app"
    synonyms 'stop'
    flag name:'port', description:"Specifies the port which the Grace application is running on (defaults to 8080 or 8443 for HTTPS)"
    flag name:'host', description:"Specifies the host the Grace application is bound to"
}
System.setProperty("run-app.running", "false")

Integer port = flag('port')?.toInteger() ?: config.getProperty('server.port', Integer) ?: 8080
String host = flag('host') ?: config.getProperty('server.address', String) ?: "localhost"
String contextPath = config.getProperty('server.context-path') ?: config.getProperty('server.contextPath') ?: ""
String managementPath = config.getProperty('management.endpoints.web.base-path') ?: config.getProperty('management.endpoints.web.basePath') ?: "/actuator"
console.updateStatus "Shutting down application..."
def url = new URL("http://$host:${port}${contextPath}${managementPath}/shutdown")
try {
	def connection = url.openConnection()
	connection.setRequestMethod("POST")
	connection.doOutput = true
	connection.connect()
	console.updateStatus connection.content.text
	while(isServerAvailable(host, port)) {
		sleep 100
	}
	console.updateStatus "Application shutdown."
	return true
	
}
catch (e) {
	console.error "Application not running.", e
	return false
}
	


