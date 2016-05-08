uniform sampler2D texture;
varying vec2 texCoord;

void main()
{
	gl_FragColor = texture2D(texture, vec2(1.0 - texCoord.y, texCoord.x));
}