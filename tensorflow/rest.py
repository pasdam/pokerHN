import sys
import BaseHTTPServer
import client_conv
from SimpleHTTPServer import SimpleHTTPRequestHandler
from BaseHTTPServer import BaseHTTPRequestHandler,HTTPServer

class MyHandler(BaseHTTPRequestHandler):
	
	#Handler for the GET requests
	def do_POST(self):
		#action=client_tf(
		length = int(self.headers.getheader('content-length'))
                data = self.rfile.read(length)
		print(data)
		action=client_conv.client_tf(data)
		self.send_response(200)
		self.send_header('Content-type','text/html')
		self.end_headers()
		# Send the html message
		self.wfile.write(action)
		return
	    
HandlerClass = MyHandler
ServerClass  = BaseHTTPServer.HTTPServer
Protocol     = "HTTP/1.0"

if sys.argv[1:]:
    port = int(sys.argv[1])
else:
    port = 8000
server_address = ('0.0.0.0', port)

HandlerClass.protocol_version = Protocol
httpd = ServerClass(server_address, HandlerClass)

sa = httpd.socket.getsockname()
print "Serving HTTP on", sa[0], "port", sa[1], "..."
httpd.handle_request()
