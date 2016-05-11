varying vec2 vTexCoord;

uniform float num;

void main()
{
	vTexCoord = gl_MultiTexCoord0;
	gl_Position = gl_Vertex + vec4(0, sin(num) * 0.02, 0, 0);
}