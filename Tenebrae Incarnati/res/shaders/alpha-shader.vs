varying vec2 texCoord;

void main()
{
	texCoord = gl_MultiTexCoord0;
	
	gl_Position = gl_Vertex;
}