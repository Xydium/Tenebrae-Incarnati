uniform sampler2D texture;
varying vec2 texCoord;

void main()
{
	gl_FragColor = texture2D(texture, vec2(texCoord.y, texCoord.x));
}