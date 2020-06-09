# type vertex
# version 400 core

layout (location = 0) in vec3 vPosition;
layout (location = 1) in vec2 vTextureCoords;
layout (location = 2) in float vTextureID;

out vec2 fTextureCoords;
out int fTextureID;

void main()
{
    fTextureCoords = vTextureCoords;
    fTextureID = int(vTextureID);
    gl_Position = vec4(vPosition, 1.0);
}


# type fragment
# version 400 core

in vec2 fTextureCoords;
in int fTextureID;

out vec4 color;

uniform sampler2D uTextures[8];

void main()
{
    color = texture(uTextures[fTextureID], fTextureCoords);
}
