# type vertex
# version 330 core

layout (location = 0) in vec3 vPosition;
layout (location = 1) in vec4 vColor;

out vec4 fColor;

void main() 
{
    fColor = vColor;
    gl_Position = vec4(vPosition * 2.0 - 1.0, 1.0);
}


# type fragment
# version 330 core

in vec4 fColor;

out vec4 color;

void main()
{
    color = fColor;
}

