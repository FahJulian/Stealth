# type vertex
# version 400 core

layout (location = 0) in vec3 vPosition;
layout (location = 1) in vec2 vTextureCoords;

out vec2 fTextureCoords;

uniform mat4 uModelMatrix;
uniform mat4 uViewMatrix;
uniform mat4 uProjectionMatrix;

void main()
{
    fTextureCoords = vTextureCoords;
    gl_Position = uProjectionMatrix * uViewMatrix * uModelMatrix * vec4(vPosition, 1.0);
}


# type fragment
# version 400 core

in vec2 fTextureCoords;

out vec4 color;

uniform float uIsTextured;
uniform sampler2D uTexture;
uniform vec4 uColor;

void main()
{
    if (uIsTextured > 0.5)
    {
        color = texture(uTexture, fTextureCoords);
    }
    else
    {
        color = uColor;
    }
}
