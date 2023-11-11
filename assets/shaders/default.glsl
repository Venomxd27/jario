#type vertex
#version  330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec4 aColor;
layout (location = 2) in vec2 aTexCoords;
layout (location = 3) in float aTexID;

uniform mat4 uProjMatrix;
uniform mat4 uViewMatrix;

out vec4 fColor;
out vec2 fTexCoords;
out float fTexID;

void main(){
    fColor = aColor;
    fTexCoords = aTexCoords;
    fTexID = aTexID;
    gl_Position = uProjMatrix * uViewMatrix * vec4(aPos, 1.0);
}

#type fragment
#version 330 core

in vec4 fColor;
in vec2 fTexCoords;
in float fTexID;

uniform sampler2D uTextures[8];

out vec4 color;

void main(){
    if (fTexID > 0){
        int id = int(fTexID);
        switch (id) {
            case 0:
            color = fColor * texture(uTextures[0], fTexCoords);
            break;
            case 1:
            color = fColor * texture(uTextures[1], fTexCoords);
            break;
            case 2:
            color = fColor * texture(uTextures[2], fTexCoords);
            break;
            case 3:
            color = fColor * texture(uTextures[3], fTexCoords);
            break;
            case 4:
            color = fColor * texture(uTextures[4], fTexCoords);
            break;
            case 5:
            color = fColor * texture(uTextures[5], fTexCoords);
            break;
            case 6:
            color = fColor * texture(uTextures[6], fTexCoords);
            break;
            case 7:
            color = fColor * texture(uTextures[7], fTexCoords);
            break;
        }
//        color = fColor * texture(uTextures[id], fTexCoords);
//        color = fColor * texture(uTextures, vec3(fTexCoords.xy, id));
    }else {
        color  = fColor;
    }
}