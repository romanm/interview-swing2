package org.j3d.p2;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GraphicsConfiguration;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.media.j3d.Alpha;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Canvas3D;
import javax.media.j3d.Material;
import javax.media.j3d.Node;
import javax.media.j3d.PointLight;
import javax.media.j3d.RotationInterpolator;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3f;
import javax.vecmath.Vector3d;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.picking.PickCanvas;
import com.sun.j3d.utils.picking.PickResult;
import com.sun.j3d.utils.universe.SimpleUniverse;

@SuppressWarnings("serial")
/**
 * Marbles.java
 * A Demonstration of Java3D - an API for the use of INSANE graphics capabilities.
 * http://java.sun.com/developer/onlineTraining/java3d/j3d_tutorial_ch1.pdf
 * @author Jamie Langille
 * @version Date: 11/06/2011
 */
public class ModelViewer extends JFrame implements MouseListener {
	
	// Main method/procedure: runs program
	public static void main(String[] args) {
		ModelViewer model = new ModelViewer();
		model.setDefaultCloseOperation(EXIT_ON_CLOSE);
		model.setVisible(true);
	}
	
	private JPanel panel;
	private BranchGroup scene;
	private PickCanvas pCanvas;
	private BranchGroup objRoot;
	private TransformGroup tg, objSpin;
	private final String[] textures = new String[]{"alien.jpg", "eye.jpg",
			"lava.jpg", "robot.jpg", "tron.jpg", "earth.jpg"};
	private ArrayList<Sphere> spheres = new ArrayList<Sphere>();
	private ArrayList<Transform3D> transformations = new ArrayList<Transform3D>();
	private ArrayList<TransformGroup> groups = new ArrayList<TransformGroup>();
	
	// Setup 3D World
	public ModelViewer() {
		
		this.setLocation(1920 / 2 - 250, 1080 / 2 - 250);
		this.setSize(500, 500);
		this.setTitle("Marbles");
		// this.setUndecorated(true);
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		objRoot = new BranchGroup();
		pCanvas = new PickCanvas(canvas3D, objRoot);
		pCanvas.setMode(PickCanvas.GEOMETRY);
		canvas3D.addMouseListener(this);
		
		panel.add(canvas3D, BorderLayout.CENTER);
		
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
		
		scene = createSceneGraph();
		scene.compile();
		
		simpleU.getViewingPlatform().setNominalViewingTransform();
		
		simpleU.addBranchGraph(scene);
		
		this.add(panel);
	}
	
	public BranchGroup createSceneGraph() {
		
		// Setup complicated stuff
		tg = new TransformGroup();
		
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		
		objSpin = new TransformGroup();
		objSpin.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		
		objRoot.addChild(tg);
		tg.addChild(objSpin);
		
		// Spheres
		for (int i = 0; i < 10; i++) {
			addSphere();
		}
		
		// Model
		objSpin.addChild(new Model(Color.RED));
		
		// Lighting
		AmbientLight aLight = new AmbientLight();
		aLight.setCapability(PointLight.ALLOW_POSITION_WRITE);
		aLight.setColor(new Color3f(Color.WHITE));
		aLight.setInfluencingBounds(new BoundingSphere());
		objRoot.addChild(aLight);
		
		// Lights
		for (int i = 0; i < 2; i++) {
			PointLight pLight = new PointLight();
			pLight.setCapability(PointLight.ALLOW_POSITION_WRITE);
			pLight.setColor(new Color3f(new Color((int) (Math.random() * 256), (int) (Math.random() * 256), (int) (Math.random() * 256))));
			pLight.setPosition(new Point3f((float) (Math.random() * 2 - 1), (float) (Math.random() * 2 - 1), (float) (Math.random() * 2 - 1)));
			pLight.setInfluencingBounds(new BoundingSphere());
			objRoot.addChild(pLight);
		}
		
		// Animation
		RotationInterpolator rot = new RotationInterpolator(new Alpha(-1, 5000), objSpin);
		rot.setSchedulingBounds(new BoundingSphere());
		objRoot.addChild(rot);
		
		// Mouse Rotation
		MouseRotate mouse = new MouseRotate(tg);
		mouse.setSchedulingBounds(new BoundingSphere());
		objRoot.addChild(mouse);
		
		objRoot.compile();
		return objRoot;
	}
	
	public void addSphere() {
		int primflags = Primitive.GENERATE_NORMALS
				+ Primitive.GENERATE_TEXTURE_COORDS;
		
		Sphere s = new Sphere(0.1f, primflags, 100);
		s.setCapability(Node.ENABLE_PICK_REPORTING);
		Appearance sApp = new Appearance();
		
		// Texturing
		TextureLoader loader = new TextureLoader(textures[(int) (Math.random() * textures.length)], "LUMINANCE", new Container());
		Texture texture = loader.getTexture();
		texture.setBoundaryModeS(Texture.WRAP);
		texture.setBoundaryModeT(Texture.WRAP);
		texture.setBoundaryColor(new Color4f(0.0f, 0.0f, 0.0f, 0.0f));
		
		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setCapability(TextureAttributes.ALLOW_MODE_READ);
		texAttr.setCapability(TextureAttributes.ALLOW_MODE_WRITE);
		texAttr.setTextureMode(Math.random() > 0.5 ? TextureAttributes.MODULATE : TextureAttributes.BLEND);
		sApp.setTexture(texture);
		sApp.setTextureAttributes(texAttr);
		
		Material sMat = new Material();
		sMat.setCapability(Material.ALLOW_COMPONENT_READ);
		sMat.setCapability(Material.ALLOW_COMPONENT_WRITE);
		sMat.setAmbientColor(new Color3f(new Color((int) (Math.random() * 128), (int) (Math.random() * 128), (int) (Math.random() * 128))));
		sApp.setMaterial(sMat);
		
		s.setAppearance(sApp);
		
		TransformGroup tg2 = new TransformGroup();
		tg2.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		tg2.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		tg2.addChild(s);
		Transform3D trans = new Transform3D();
		trans.setTranslation(new Vector3d((float) (Math.random() * 0.8 - 0.4), (float) (Math.random() * 0.8 - 0.4), (float) (Math.random() * 0.8 - 0.4)));
		tg2.setTransform(trans);
		
		objSpin.addChild(tg2);
		spheres.add(s);
		transformations.add(trans);
		groups.add(tg2);
	}
	
	// Mouse Input
	public void mouseClicked(MouseEvent e) {
		
	}
	
	public void mouseEntered(MouseEvent e) {
		
	}
	
	public void mouseExited(MouseEvent e) {
		
	}
	
	public void mousePressed(MouseEvent e) {
		pCanvas.setShapeLocation(e);
		PickResult result = pCanvas.pickClosest();
		if (result != null) {
			Object obj = result.getNode(PickResult.PRIMITIVE);
			if (obj instanceof Sphere) {
				Sphere s = (Sphere) obj;
				if (s != null) {
					if (e.getButton() == MouseEvent.BUTTON1) {
						s.getAppearance().getMaterial().setAmbientColor(new Color3f(new Color((int) (Math.random() * 128), (int) (Math.random() * 128), (int) (Math.random() * 128))));
					} else if (e.getButton() == MouseEvent.BUTTON3) {
						Transform3D trans = transformations.get(spheres.indexOf(s));
						trans.setTranslation(new Vector3d((float) (Math.random() * 1.8 - 0.9), (float) (Math.random() * 1.8 - 0.9), (float) (Math.random() * 1.8 - 0.9)));
						groups.get(spheres.indexOf(s)).setTransform(trans);
					} else {
						s.getAppearance().getTextureAttributes().setTextureMode(s.getAppearance().getTextureAttributes().getTextureMode() == TextureAttributes.BLEND ? TextureAttributes.MODULATE : TextureAttributes.BLEND);
					}
				}
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		
	}
}