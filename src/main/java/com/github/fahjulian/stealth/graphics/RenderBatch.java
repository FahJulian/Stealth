package com.github.fahjulian.stealth.graphics;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_DYNAMIC_DRAW;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glBufferSubData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.util.ArrayList;
import java.util.List;

import com.github.fahjulian.stealth.AApplication;
import com.github.fahjulian.stealth.core.Log;
import com.github.fahjulian.stealth.entity.Transform;
import com.github.fahjulian.stealth.entity.component.SpriteComponent;
import com.github.fahjulian.stealth.util.ResourcePool;

import org.joml.Vector2f;

/**
 * Class to group renderable objects together and draw them in one call.
 */
public class RenderBatch implements Comparable<RenderBatch> {

    private final int size;
    private final int zIndex;
    private final Shader shader;
    private final SpriteComponent[] sprites;
    private final float[] vertices;
    private final List<Texture> textures;
    private int spriteCount;
    private int vaoID, vboID;

    private static final int MAX_TEXTURES = 7;
    private static final int 
        POS_BUFFER_SIZE = 2, COLOR_BUFFER_SIZE = 4, TEXTURE_COORDS_BUFFER_SIZE = 2,
        TEXTURE_ID_BUFFER_SIZE = 1,

        POS_BUFFER_OFFSET = 0 * Float.BYTES,
        COLOR_BUFFER_OFFSET = POS_BUFFER_OFFSET + POS_BUFFER_SIZE * Float.BYTES,
        TEXTURE_COORDS_BUFFER_OFFSET = COLOR_BUFFER_OFFSET + COLOR_BUFFER_SIZE * Float.BYTES,
        TEXTURE_ID_BUFFER_OFFSET = TEXTURE_COORDS_BUFFER_OFFSET + TEXTURE_COORDS_BUFFER_SIZE * Float.BYTES,

        STRIDE_SIZE = POS_BUFFER_SIZE + COLOR_BUFFER_SIZE + TEXTURE_COORDS_BUFFER_SIZE + TEXTURE_ID_BUFFER_SIZE;

    /**
     * Construct a new render batch
     * @param size The maximum amount of objects the batch can contain
     * @param zIndex The zIndex of the layer the batch will be on
     * @param shaderPath The path on the system to the shader to use 
     */
    public RenderBatch(int size, int zIndex, String shaderPath) {
        this.size = size;
        this.zIndex = zIndex;
        this.shader = ResourcePool.getShader(shaderPath);
        this.sprites = new SpriteComponent[size];
        this.textures = new ArrayList<Texture>();
        this.vertices = new float[size * 4 * STRIDE_SIZE];
        this.spriteCount = 0;
	}

    /**
     * Setup opengl properties
     */
	public void init() {
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, vertices.length * Float.BYTES, GL_DYNAMIC_DRAW);

        int eboID = glGenBuffers();
        int[] indices = generateEBO();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, POS_BUFFER_SIZE, GL_FLOAT, false, STRIDE_SIZE * Float.BYTES, POS_BUFFER_OFFSET);
        glVertexAttribPointer(1, COLOR_BUFFER_SIZE, GL_FLOAT, false, STRIDE_SIZE * Float.BYTES, COLOR_BUFFER_OFFSET);
        glVertexAttribPointer(2, TEXTURE_COORDS_BUFFER_SIZE, GL_FLOAT, false, STRIDE_SIZE * Float.BYTES, TEXTURE_COORDS_BUFFER_OFFSET);
        glVertexAttribPointer(3, TEXTURE_ID_BUFFER_SIZE, GL_FLOAT, false, STRIDE_SIZE * Float.BYTES, TEXTURE_ID_BUFFER_OFFSET);
    }
    
    /**
     * Render the batch
     */
	public void render() {
        rebuffer();

        shader.bind();
        shader.upload("uProjection", AApplication.get().getScene().getCamera().getProjectionMatrix());
        shader.upload("uView", AApplication.get().getScene().getCamera().getViewMatrix());

        for (int i = 0; i < textures.size(); i++) {
            glActiveTexture(GL_TEXTURE0 + i);
            textures.get(i).bind();
        }

        shader.upload("UTextures", new int[] { 0, 1, 2, 3, 4, 5, 6, 7 });

        draw();

        for (int i = 0; i < textures.size(); i++)
            textures.get(i).unbind();
        
        shader.unbind();
    }
     
    private void rebuffer() {
        boolean rebuffer = false;
        for (int i = 0; i < spriteCount; i++) {
            SpriteComponent sprite = sprites[i];
            if (sprite.hasChanges()) {
                loadSpriteData(sprite, i);
                sprite.cleanChanges();
                rebuffer = true;
            }
        }

        if (rebuffer) {
            glBindBuffer(GL_ARRAY_BUFFER, vboID);
            glBufferSubData(GL_ARRAY_BUFFER, 0, vertices);
            glBindBuffer(GL_ARRAY_BUFFER, 0);
        }
    }

    private void draw() {
        glBindVertexArray(vaoID);
        glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);
        glEnableVertexAttribArray(2);
        glEnableVertexAttribArray(3);

        glDrawElements(GL_TRIANGLES, spriteCount * 6, GL_UNSIGNED_INT, 0);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);
        glDisableVertexAttribArray(2);
        glDisableVertexAttribArray(3);
        glBindVertexArray(0);
    }

	private void loadSpriteData(SpriteComponent sprite, int index) {
        int offset = index * 4 * STRIDE_SIZE;
        Vector2f[] textureCoords = sprite.getTextureCoords();
        Transform transform = sprite.getEntity().getTransform();
        int textureID = textures.indexOf(sprite.getTexture());

        for (int corner = 0; corner < 4; corner++) {
            vertices[offset + 0] = transform.getPosition().x + (corner % 2) * transform.getScale().x;
            vertices[offset + 1] = transform.getPosition().x + (corner / 2) * transform.getScale().y;
            vertices[offset + 2] = 1.0f; 
            vertices[offset + 3] = 1.0f; 
            vertices[offset + 4] = 1.0f; 
            vertices[offset + 5] = 1.0f;
            vertices[offset + 6] = textureCoords[corner].x; 
            vertices[offset + 7] = textureCoords[corner].y; 
            vertices[offset + 8] = (float) textureID;
            offset += STRIDE_SIZE;
        }
    }

    private int[] generateEBO() {
        int[] indices = new int[this.size * 6];
        for (int i = 0; i < this.size; i++) {
            indices[i * 6 + 0] = 1 + i * 4;
            indices[i * 6 + 1] = 3 + i * 4;
            indices[i * 6 + 2] = 2 + i * 4;
            indices[i * 6 + 3] = 2 + i * 4;
            indices[i * 6 + 4] = 0 + i * 4;
            indices[i * 6 + 5] = 1 + i * 4;
        }

        return indices;
    }

    /**
     * Add a SpriteComponent to the batch
     * @param sprite The SpriteComponent to add
     */
	public void add(SpriteComponent sprite) {
        Texture texture = sprite.getTexture();
        if (spriteCount >= size) {
            Log.warn("(RenderBatch) Batch is full. Cant add sprite %s", sprite);
            return;
        } else if (!textures.contains(texture) && textures.size() >= MAX_TEXTURES) {
            Log.warn("(RenderBatch) Batch has no room for another texture. Cant add sprite %s", sprite);
        }

        int idx = spriteCount++;
        sprites[idx] = sprite;

        if (!textures.contains(texture))
            textures.add(texture);

        loadSpriteData(sprite, idx);
	}

    /**
     * Checks if there is enough room for te SpriteComponent to be added
     * @param sprite The SpriteComponent to check if theres room for
     * @return Whether or not there is room for the SpriteComponent
     */
    public boolean hasRoomFor(SpriteComponent sprite) {
		return (spriteCount < size) && ( textures.size() < 7 || textures.contains(sprite.getTexture()) );
	}

    public int getZIndex() {
        return zIndex;
    }

    @Override
    public int compareTo(RenderBatch o) {
        return Integer.compare(this.zIndex, o.zIndex);
    }
}
