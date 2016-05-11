uniform sampler2D texture;

uniform float num;

varying vec2 vTexCoord;

//layout(location = 3) out vec3 position;

void main()
{
	vec2 aux = vTexCoord;
	//aux.x = aux.x + sin(10 * aux.y + num * 30) * 0.05;
	
	vec4 col = texture2D(texture, aux);
	//float avg = (col.x + col.y + col.z) / 3.0;
	
	float dist = sqrt(aux.x * aux.x + aux.y * aux.y);
	
	gl_FragColor = col * (abs(sin(num)) / dist);
	
	//gl_FragColor = vec4(col.r * abs(sin(3 * (aux.y + num))), col.g * abs(cos(3 * (aux.y + num))),
	//	col.b * abs(tan(3 * (aux.y + num))), col.a);
	//gl_FragColor = vec4(avg, avg, avg, col.w);
	//gl_FragColor = vec4(1.0, 0.0, 0.0, 1.0);
}