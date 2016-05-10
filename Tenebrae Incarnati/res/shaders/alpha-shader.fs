uniform sampler2D texture;
uniform float alpha;
varying vec2 texCoord;

void main()
{
	vec4 tex = texture2D(texture, vec2(1.0 - texCoord.y, texCoord.x))
	gl_FragColor = vec4(tex.x, tex.y, tex.z, alpha);
}