# type vertex
# version 400 core

layout (location = 0) in vec3 position;

out vec4 fColor;

uniform mat4 uProjectionMatrix;
uniform mat4 uModelMatrix;
uniform vec4 uColor;

void main() 
{
    fColor = uColor;
    gl_Position = uProjectionMatrix * uModelMatrix * vec4(position, 1.0);
}


# type fragment
# version 330 core

in vec4 fColor;

out vec4 color;

void main() 
{
    color = fColor;
}
