# type vertex
# version 450 core

layout (location = 0) in vec3 vPosition;
layout (location = 1) in vec2 vTextureCoords;
layout (location = 2) in float vTextureSlot;

out vec2 fTextureCoords;
out float fTextureSlot;

uniform mat4 uViewMatrix;
uniform mat4 uProjectionMatrix;

void main()
{
    fTextureCoords = vTextureCoords;
    fTextureSlot = vTextureSlot;
    gl_Position = uProjectionMatrix * uViewMatrix * vec4(vPosition, 1.0);
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

    switch (textureSlot)
    {
        case 0:
            color = texture(uTextures[0], fTextureCoords);
            return;
        case 1:
            color = texture(uTextures[1], fTextureCoords);
            return;
        case 2:
            color = texture(uTextures[2], fTextureCoords);
            return;
        case 3:
            color = texture(uTextures[3], fTextureCoords);
            return;
        case 4:
            color = texture(uTextures[4], fTextureCoords);
            return;
        case 5:
            color = texture(uTextures[5], fTextureCoords);
            return;
        case 6:
            color = texture(uTextures[6], fTextureCoords);
            return;
        case 7:
            color = texture(uTextures[7], fTextureCoords);
            return;
        case 8:
            color = texture(uTextures[8], fTextureCoords);
            return;
        case 9:
            color = texture(uTextures[9], fTextureCoords);
            return;
        case 10:
            color = texture(uTextures[10], fTextureCoords);
            return;
        case 11:
            color = texture(uTextures[11], fTextureCoords);
            return;
        case 12:
            color = texture(uTextures[12], fTextureCoords);
            return;
        case 13:
            color = texture(uTextures[13], fTextureCoords);
            return;
        case 14:
            color = texture(uTextures[14], fTextureCoords);
            return;
        case 15:
            color = texture(uTextures[15], fTextureCoords);
            return;
    }
}
