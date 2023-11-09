#type vertex
#version 330 core
layout (location=0) in vec3 aPos;
layout (location=1) in vec4 aColor;
layout (location=2) in vec2 aTexCoords;
layout (location=3) in float aTexID;


uniform mat4 uProjMatrix;
uniform mat4 uViewMatrix;

out vec4 fColor;
out vec2 fTexCoords;
out float fTexID;

void main() {
    fColor =  aColor;
    fTexCoords = aTexCoords;
    fTexID = aTexID;
    gl_Position = uProjMatrix * uViewMatrix * vec4(aPos , 1.0);
}

#type fragment
#version 330 core

in vec4 fColor;
in vec2 fTexCoords;
in float fTexID;

uniform sampler2DArray uTextures;

out vec4 color;

void main() {
    if (fTexID > 0){
        int id = int(fTexID);
        color = fColor * texture(uTextures, vec3(fTexCoords.xy, fTexID)); // -> Other way to write
//        color = fColor * texture(uTextures[id], fTexCoords);   // -> Not working
//        color = vec4(fTexCoords, 0, 1);
    }
    else{
        color = fColor;
    }
}
