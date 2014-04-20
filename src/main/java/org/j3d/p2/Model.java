package org.j3d.p2;

import java.awt.Color;
import java.awt.Container;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.media.j3d.Appearance;
import javax.media.j3d.Geometry;
import javax.media.j3d.Material;
import javax.media.j3d.Shape3D;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.TriangleStripArray;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3f;
import com.sun.j3d.utils.image.TextureLoader;

public class Model extends Shape3D {

	public Model(Color c) {
		this.setGeometry(makeGeometry());
//		this.setAppearance(makeAppearance());
		this.setCapability(ENABLE_PICK_REPORTING);
	}
	
	@SuppressWarnings("unused")
	private Appearance makeAppearance(Color3f c) {
		Appearance app = new Appearance();
		Material matt = new Material();
		matt.setAmbientColor(c);
		matt.setCapability(Material.ALLOW_COMPONENT_READ);
		matt.setCapability(Material.ALLOW_COMPONENT_WRITE);
		app.setMaterial(matt);
		
		TextureLoader loader = new TextureLoader("tron.jpg", "LUMINANCE", new Container());
		Texture texture = loader.getTexture();
		texture.setBoundaryModeS(Texture.WRAP);
		texture.setBoundaryModeT(Texture.WRAP);
		texture.setBoundaryColor(new Color4f(0.0f, 0.0f, 0.0f, 0.0f));
		
		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setCapability(TextureAttributes.ALLOW_MODE_READ);
		texAttr.setCapability(TextureAttributes.ALLOW_MODE_WRITE);
		texAttr.setTextureMode(TextureAttributes.BLEND);
		app.setTexture(texture);
		app.setTextureAttributes(texAttr);
		return app;
	}
	
	private Geometry makeGeometry() {
		Scanner in;
		try {
			in = new Scanner(new File("model.txt"));
			TriangleStripArray tsa;
			int[] stripCounts = new int[in.nextInt()];
			for (int i = 0; i < stripCounts.length; i++) {
				stripCounts[i] = in.nextInt();
			}
			Point3f[] coords = new Point3f[in.nextInt()];
			for (int i = 0; i < coords.length; i++) {
				float x = in.nextFloat();
				float y = in.nextFloat();
				float z = in.nextFloat();
				coords[i] = new Point3f(x, y, z);
			}
			Color3f[] cols = new Color3f[coords.length];
			for (int i = 0; i < cols.length; i++) {
				cols[i] = new Color3f(new Color((int) (Math.random() * 128), (int) (Math.random() * 128), (int) (Math.random() * 128)));
			}
			tsa = new TriangleStripArray(coords.length, TriangleStripArray.COORDINATES|TriangleStripArray.COLOR_3, stripCounts);
			tsa.setColors(0, cols);
			tsa.setCoordinates(0, coords);
			in.close();
			return tsa;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
}