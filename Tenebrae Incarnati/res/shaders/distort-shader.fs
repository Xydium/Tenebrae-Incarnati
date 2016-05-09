
uniform sampler2D currentTexture;
//uniform sampler2D distortionMapTexture;

uniform float time;

uniform float frequency;
uniform float amplitude;

//uniform float distortionFactor;
//uniform float riseFactor;

varying vec2 texCoord;

float clamp(float a, float min, float max)
{
	if (a < min)
		return min;
	
	if (a > max)
		return max;
	
	return a;
}

void main()
{
	vec2 aux = vec2(1.0 - texCoord.y, texCoord.x);
	aux.x = clamp(sin(30 * aux.y + frequency * time) * amplitude + aux.x, 0, 1);
	aux.y = clamp(sin(30 * aux.x + frequency * time) * amplitude + aux.y, 0, 1);
	
	gl_FragColor = texture2D(currentTexture, aux);
}

/*void main()
{
	vec2 distortionMapCoord = texCoord.st;
	
	distortionMapCoord.t -= time * riseFactor;
	
	vec4 distortionMapValue = texture2D(distortionMapTexture, distortionMapCoord);
	
	vec2 distortionPositionOffset = distortionMapValue.xy;
	distortionPositionOffset -= vec2(0.5f, 0.5f);
	distortionPositionOffset *= 2.f;
	
	distortionPositionOffset *= distortionFactor;
	
	vec2 distortionUnused = distortionMapValue.zw;
	
	distortionPositionOffset *= (1.f - texCoord.t);
	
	vec2 distortedTexCoord = texCoord.st + distortionPositionOffset;
	
	//gl_FragColor = gl_Color * texture2D(currentTexture, distortedTexCoord);
	gl_FragColor = gl_Color * texture2D(currentTexture, distortedTexCoord);
}*/