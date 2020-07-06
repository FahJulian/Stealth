# type vertex
# version 450 core

layout (location = 0) in vec3 vPosition;
layout (location = 1) in vec2 vTextureCoords;
layout (location = 2) in float vTextureSlot;

out vec2 fTextureCoords;
out float fTextureSlot;

uniform mat4 uProjectionMatrix;

void main()
{
    fTextureCoords = vTextureCoords;
    fTextureSlot = vTextureSlot;
    gl_Position = uProjectionMatrix * vec4(vPosition, 1.0);
}


# type fragment
# version 450 core

in vec2 fTextureCoords;
in float fTextureSlot;

out vec4 color;

uniform sampler2D uTextures[16];

void main()
{
    int textureSlot = int(fTextureSlot);
    color = texture(uTextures[textureSlot], fTextureCoords);
}
