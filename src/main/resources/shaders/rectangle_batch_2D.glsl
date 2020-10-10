# type vertex
# version 450 core

layout (location = 0) in vec3 vPosition;
layout (location = 1) in vec4 vColor;
layout (location = 2) in vec2 vTextureCoords;
layout (location = 3) in float vTextureSlot;

out vec4 fColor;
out vec2 fTextureCoords;
out float fTextureSlot;

uniform mat4 uViewMatrix;
uniform mat4 uProjectionMatrix;

void main()
{
    gl_Position = uProjectionMatrix * uViewMatrix * vec4(vPosition, 1.0);

    fColor = vColor;
    fTextureCoords = vTextureCoords;
    fTextureSlot = vTextureSlot;
}

# type fragment
# version 450 core

in vec4 fColor;
in vec2 fTextureCoords;
in float fTextureSlot;

out vec4 color;

uniform sampler2D uTextures[16];

void main()
{
    int textureSlot = int(fTextureSlot);

    // if (textureSlot == -1)
    // {
    //     color = vec4(0.0, 0.0, 1.0, 1.0);
    //         color = vec4(fTextureCoords[0], fTextureCoords[1], 0.0, 1.0);
    //     // color = fColor;
    //     return;
    // }

    switch (textureSlot)
    {
        case -1:
            color = fColor;
            return;
        case 0:
            color = fColor * texture(uTextures[0], fTextureCoords);
            return;
        case 1:
            color = fColor * texture(uTextures[1], fTextureCoords);
            return;
        case 2:
            color = fColor * texture(uTextures[2], fTextureCoords);
            return;
        case 3:
            color = fColor * texture(uTextures[3], fTextureCoords);
            return;
        case 4:
            color = fColor * texture(uTextures[4], fTextureCoords);
            return;
        case 5:
            color = fColor * texture(uTextures[5], fTextureCoords);
            return;
        case 6:
            color = fColor * texture(uTextures[6], fTextureCoords);
            return;
        case 7:
            color = fColor * texture(uTextures[7], fTextureCoords);
            return;
        case 8:
            color = fColor * texture(uTextures[8], fTextureCoords);
            return;
        case 9:
            color = fColor * texture(uTextures[9], fTextureCoords);
            return;
        case 10:
            color = fColor * texture(uTextures[10], fTextureCoords);
            return;
        case 11:
            color = fColor * texture(uTextures[11], fTextureCoords);
            return;
        case 12:
            color = fColor * texture(uTextures[12], fTextureCoords);
            return;
        case 13:
            color = fColor * texture(uTextures[13], fTextureCoords);
            return;
        case 14:
            color = fColor * texture(uTextures[14], fTextureCoords);
            return;
        case 15:
            color = fColor * texture(uTextures[15], fTextureCoords);
            return;
    }
}
