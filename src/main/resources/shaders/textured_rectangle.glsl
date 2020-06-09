# type vertex
# version 400 core

layout (location = 0) in vec3 vPosition;
layout (location = 1) in vec2 vTextureCoords;

out vec2 fTextureCoords;

uniform mat4 uProjectionMatrix;
uniform mat4 uModelMatrix;

void main()
{
    fTextureCoords = vTextureCoords;
    gl_Position = uProjectionMatrix * uModelMatrix * vec4(vPosition, 1.0);
}


# type fragment
# version 400 core

in vec2 fTextureCoords;

out vec4 color;

uniform sampler2D uTexture;

void main()
{
    // color = vec4(1.0, 0.0, 0.0, 1.0);
    color = texture(uTexture, fTextureCoords);
}
