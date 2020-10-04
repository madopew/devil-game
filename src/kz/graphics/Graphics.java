package kz.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
//������ ����� ������ � ���� ������ ������
public class Graphics {
	//������ �������� � ������ ����������� � ������� ���������
	//��� ��������
	public static void drawImage(ImageResource image, float x, float y, float width, float height) {
		//�������� ��� ������� � ������� ���������
		drawImageRot(image, x, y, width, height, 0, 0, 0, 1);
	}
	//������ �������� � ������ ����������� � ������� ��������� � ���������
	public static void drawImageRot(ImageResource image, float x, float y, float width, float height, float rotation, int vx, int vy, int vz) {
		//�������� ��������
		GL2 gl = EventListener.getGL();
		//������� �������� �� ��������
		Texture tex = image.getTexture();
		//���� �������� ���������� ��������� �� � ��������
		if(tex != null) {
			gl.glBindTexture(GL2.GL_TEXTURE_2D, tex.getTextureObject());
		}
		//��������� ������ ��������� � �������� ��������������
		//������� ���� ����������
		//(����� ������� ��� ������ ���������� ���)
		gl.glTranslatef(x+width/2, y+height/2, 0);
		//������������ ������ ������
		gl.glRotatef(rotation, vx, vy, vz);
		//������������� ����� ���� ���������
		gl.glColor4f(1, 1, 1, 1);
		//����� ���������� - ���������������
		gl.glBegin(GL2.GL_QUADS);
		//������ ��������������� ������������ ��� ������
		//� ����� ���������� �������� � ���������� ������������������
		gl.glTexCoord2f(0, 0);
		gl.glVertex2f(-width/2, -height/2);
		
		gl.glTexCoord2f(1, 0);
		gl.glVertex2f(width/2, -height/2);
		
		gl.glTexCoord2f(1, 1);
		gl.glVertex2f(width/2, height/2);
		
		gl.glTexCoord2f(0, 1);
		gl.glVertex2f(-width/2, height/2);
		
		gl.glEnd();
		gl.glBindTexture(GL2.GL_TEXTURE_2D, 0);
		//������� ������������ ������������ ����� � ������� �����
		gl.glRotatef(-rotation, vx, vy, vz);
		gl.glTranslatef(-x-width/2, -y-height/2, 0);
		gl.glFlush();
	}
}
