package kz.graphics;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.texture.Texture;
//данный класс рисует в окне разные фигуры
public class Graphics {
	//рисует картинку в данных координатах с данными размерами
	//без поворота
	public static void drawImage(ImageResource image, float x, float y, float width, float height) {
		//вызывает эту функцию с нулевым поворотом
		drawImageRot(image, x, y, width, height, 0, 0, 0, 1);
	}
	//рисует картинку в данных координатах с данными размерами и поворотом
	public static void drawImageRot(ImageResource image, float x, float y, float width, float height, float rotation, int vx, int vy, int vz) {
		//получает оболочку
		GL2 gl = EventListener.getGL();
		//достает текстуру из картинки
		Texture tex = image.getTexture();
		//если текстура существует связывает ее с объектом
		if(tex != null) {
			gl.glBindTexture(GL2.GL_TEXTURE_2D, tex.getTextureObject());
		}
		//переносит начало координат в середину прямоугольника
		//который надо отобразить
		//(чтобы поворот был вокруг правильной оси)
		gl.glTranslatef(x+width/2, y+height/2, 0);
		//поворачивает вокруг центра
		gl.glRotatef(rotation, vx, vy, vz);
		//устанавливает белый цвет наложения
		gl.glColor4f(1, 1, 1, 1);
		//режим прорисовки - четырехугольник
		gl.glBegin(GL2.GL_QUADS);
		//рисует четырехугольник относительно его центра
		//а также отображает текстуру в правильном последовательности
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
		//обратно поворачивает координатную сетку и очищает буфер
		gl.glRotatef(-rotation, vx, vy, vz);
		gl.glTranslatef(-x-width/2, -y-height/2, 0);
		gl.glFlush();
	}
}
