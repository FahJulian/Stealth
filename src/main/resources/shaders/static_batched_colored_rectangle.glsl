# type vertex
# version 450 core

layout (location = 0) in vec3 vPosition;
layout (location = 1) in vec4 vColor;

out vec4 fColor;

uniform mat4 uProjectionMatrix;

void main()
{
    fColor = vColor;
    gl_Position = uProjectionMatrix * vec4(vPosition, 1.0);
}


# type fragment
# version 450 core

in vec4 fColor;

out vec4 color;

void main()
{
    color = fColor;
}
