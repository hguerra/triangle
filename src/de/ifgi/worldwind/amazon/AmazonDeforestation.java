package de.ifgi.worldwind.amazon;

import gov.nasa.worldwind.BasicModel;
import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.AnnotationLayer;
import gov.nasa.worldwind.layers.CompassLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.layers.Earth.CountryBoundariesLayer;
import gov.nasa.worldwind.render.ScreenAnnotation;
import gov.nasa.worldwindx.examples.util.PowerOfTwoPaddedImage;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import kinect.SkeletonKinectHandler;
import de.ifgi.data.DataRetriever;
import de.ifgi.worldwind.layer.AmazonGDPLayer;
import de.ifgi.worldwind.layer.AmazonLanduseLayer2;
import de.ifgi.worldwind.layer.AmazonPopAcumLayer;

/**
 * This is the most basic World Wind program.
 * 
 * @version $Id: HelloWorldWind.java 4869 2008-03-31 15:56:36Z tgaskins $
 */
public class AmazonDeforestation {

	@SuppressWarnings("serial")
	public static class AppFrame extends JFrame {

		RenderableLayer ama2002;
		RenderableLayer ama2003;
		RenderableLayer ama2004;
		RenderableLayer ama2005;
		RenderableLayer ama2006;
		RenderableLayer ama2007;
		RenderableLayer ama2008;

		RenderableLayer ama2002a;
		RenderableLayer ama2003a;
		RenderableLayer ama2004a;
		RenderableLayer ama2005a;
		RenderableLayer ama2006a;
		RenderableLayer ama2007a;
		RenderableLayer ama2008a;

		RenderableLayer ama2002b;
		RenderableLayer ama2003b;
		RenderableLayer ama2004b;
		RenderableLayer ama2005b;
		RenderableLayer ama2006b;
		RenderableLayer ama2007b;
		RenderableLayer ama2008b;

		AnnotationLayer anoLayer2002;
		AnnotationLayer anoLayer2003;
		AnnotationLayer anoLayer2004;
		AnnotationLayer anoLayer2005;
		AnnotationLayer anoLayer2006;
		AnnotationLayer anoLayer2007;
		AnnotationLayer anoLayer2008;

		AnnotationLayer anoLayer2002a;
		AnnotationLayer anoLayer2003a;
		AnnotationLayer anoLayer2004a;
		AnnotationLayer anoLayer2005a;
		AnnotationLayer anoLayer2006a;
		AnnotationLayer anoLayer2007a;
		AnnotationLayer anoLayer2008a;

		AnnotationLayer anoLayer2002b;
		AnnotationLayer anoLayer2003b;
		AnnotationLayer anoLayer2004b;
		AnnotationLayer anoLayer2005b;
		AnnotationLayer anoLayer2006b;
		AnnotationLayer anoLayer2007b;
		AnnotationLayer anoLayer2008b;

		AnnotationLayer generalAnoLayer2002;
		AnnotationLayer generalAnoLayer2003;
		AnnotationLayer generalAnoLayer2004;
		AnnotationLayer generalAnoLayer2005;
		AnnotationLayer generalAnoLayer2006;
		AnnotationLayer generalAnoLayer2007;
		AnnotationLayer generalAnoLayer2008;

		AnnotationLayer generalAnoLayer2002a;
		AnnotationLayer generalAnoLayer2003a;
		AnnotationLayer generalAnoLayer2004a;
		AnnotationLayer generalAnoLayer2005a;
		AnnotationLayer generalAnoLayer2006a;
		AnnotationLayer generalAnoLayer2007a;
		AnnotationLayer generalAnoLayer2008a;

		AnnotationLayer generalAnoLayer2002b;
		AnnotationLayer generalAnoLayer2003b;
		AnnotationLayer generalAnoLayer2004b;
		AnnotationLayer generalAnoLayer2005b;
		AnnotationLayer generalAnoLayer2006b;
		AnnotationLayer generalAnoLayer2007b;
		AnnotationLayer generalAnoLayer2008b;

		ScreenAnnotation logoAnnotation;

		boolean year2002 = false;

		String activeLayer = "c";
		ArrayList activeLayers = new ArrayList();

		private AppFrameController controller;

		private WorldWindowGLCanvas wwd;

		private SkeletonKinectHandler kinectHandler;

		private int height = 768;
		private int width = 1024;

		public static final double INITIAL_ZOOM = 2.3e7;
		public static final Position PARA_POS = Position.fromDegrees(-4.72826,
				-52.302247, 7000000);

		int layerChanger = 0;

		private JLayeredPane layeredPane;

		public AppFrame() {

			wwd = new WorldWindowGLCanvas();
			wwd.setPreferredSize(new java.awt.Dimension(width, height));
			wwd.setModel(new BasicModel());
			wwd.setBounds(0, 0, width + 1, height + 1); // +1 because without it
														// the camera image isnt
														// shown

			layeredPane = new JLayeredPane();
			layeredPane.setPreferredSize(new java.awt.Dimension(width, height));

			layeredPane.add(wwd, java.awt.BorderLayout.CENTER);

			layeredPane.setBounds(0, 0, width, height);

			layeredPane.doLayout();

			this.printLogo();

			addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosing(WindowEvent event) {
					event.getWindow().setVisible(false);
					event.getWindow().dispose();
					System.exit(0);
				}
			});
			this.setUndecorated(true);
			this.getContentPane()
					.add(layeredPane, java.awt.BorderLayout.CENTER);
			this.pack();
			this.setBounds(0, 0, width, height);

			this.controller = new AppFrameController(this);

			DataRetriever dataR = new DataRetriever();
			dataR.queryForStates();

			/*
			 * 
			 * Für die Seiten
			 */

			addGDPlayer(dataR); // economical

			addLanduseLayer(dataR); // ecological

			year2002 = true; // social
			addAcumPoplayer(dataR); // social

			removeCompass(this.getWwd());

			initKinectHandler();

		}

		private void printLogo() {

			PowerOfTwoPaddedImage pic = PowerOfTwoPaddedImage
					.fromPath("images/ccst-novo.png");
			AnnotationLayer annLayer = new AnnotationLayer();

			ScreenAnnotation logoDWIH = new ScreenAnnotation("", new Point(780,
					530));

			logoDWIH.getAttributes().setImageSource(pic.getPowerOfTwoImage());
			logoDWIH.getAttributes().setImageRepeat(AVKey.REPEAT_NONE);
			logoDWIH.getAttributes().setAdjustWidthToText(AVKey.SIZE_FIXED);
			logoDWIH.getAttributes().setDrawOffset(new Point(100, 0));
			logoDWIH.getAttributes().setHighlightScale(1);

			logoDWIH.getAttributes().setInsets(new Insets(0, 40, 0, 0));
			logoDWIH.getAttributes().setSize(new Dimension(265, 150));

			logoDWIH.getAttributes().setImageScale(0.22);
			logoDWIH.getAttributes().setImageOffset(new Point(10, 10));

			annLayer.addAnnotation(logoDWIH);
			insertBeforeBeforeCompass(this.wwd, annLayer);

		}

		private void initKinectHandler() {
			kinectHandler = new SkeletonKinectHandler(this);
			kinectHandler.setBounds(15, 585, 224, 168);
			layeredPane.add(kinectHandler, new Integer(
					JLayeredPane.DEFAULT_LAYER.intValue() + 1));
		}

		/**
		 * @author Umut Tas
		 */
		public void addLanduseLayer(DataRetriever dataR) {
			ama2004 = new AmazonLanduseLayer2(dataR.getMuniData(),
					dataR.getMesoRegions(), "2004");
			anoLayer2004 = ((AmazonLanduseLayer2) ama2004).addAnnotations();
			generalAnoLayer2004 = ((AmazonLanduseLayer2) ama2004)
					.generalInformationLayer();

			ama2005 = new AmazonLanduseLayer2(dataR.getMuniData(),
					dataR.getMesoRegions(), "2005");
			anoLayer2005 = ((AmazonLanduseLayer2) ama2005).addAnnotations();
			generalAnoLayer2005 = ((AmazonLanduseLayer2) ama2005)
					.generalInformationLayer();

			ama2006 = new AmazonLanduseLayer2(dataR.getMuniData(),
					dataR.getMesoRegions(), "2006");
			anoLayer2006 = ((AmazonLanduseLayer2) ama2006).addAnnotations();
			generalAnoLayer2006 = ((AmazonLanduseLayer2) ama2006)
					.generalInformationLayer();

			ama2007 = new AmazonLanduseLayer2(dataR.getMuniData(),
					dataR.getMesoRegions(), "2007");
			anoLayer2007 = ((AmazonLanduseLayer2) ama2007).addAnnotations();
			generalAnoLayer2007 = ((AmazonLanduseLayer2) ama2007)
					.generalInformationLayer();

			ama2008 = new AmazonLanduseLayer2(dataR.getMuniData(),
					dataR.getMesoRegions(), "2008");
			anoLayer2008 = ((AmazonLanduseLayer2) ama2008).addAnnotations();
			generalAnoLayer2008 = ((AmazonLanduseLayer2) ama2008)
					.generalInformationLayer();

			insertBeforeBeforeCompass(this.getWwd(), ama2004);
			insertBeforeBeforeCompass(this.getWwd(), anoLayer2004);
			insertBeforeBeforeCompass(this.getWwd(), generalAnoLayer2004);

			activeLayers.add(ama2004);
			activeLayers.add(anoLayer2004);
			activeLayers.add(generalAnoLayer2004);

			insertBeforeBeforeCompass(this.getWwd(),
					new CountryBoundariesLayer());

			controller.flyToPosition(PARA_POS, INITIAL_ZOOM);

		}

		/**
		 * @author Umut Tas
		 */
		public void addGDPlayer(DataRetriever dataR) {
			ama2004a = new AmazonGDPLayer(dataR.getMuniData(), "2004");
			anoLayer2004a = ((AmazonGDPLayer) ama2004a).addAnnotations();
			generalAnoLayer2004a = ((AmazonGDPLayer) ama2004a)
					.generalInformationLayer();

			ama2005a = new AmazonGDPLayer(dataR.getMuniData(), "2005");
			anoLayer2005a = ((AmazonGDPLayer) ama2005a).addAnnotations();
			generalAnoLayer2005a = ((AmazonGDPLayer) ama2005a)
					.generalInformationLayer();

			ama2006a = new AmazonGDPLayer(dataR.getMuniData(), "2006");
			anoLayer2006a = ((AmazonGDPLayer) ama2006a).addAnnotations();
			generalAnoLayer2006a = ((AmazonGDPLayer) ama2006a)
					.generalInformationLayer();

			ama2007a = new AmazonGDPLayer(dataR.getMuniData(), "2007");
			anoLayer2007a = ((AmazonGDPLayer) ama2007a).addAnnotations();
			generalAnoLayer2007a = ((AmazonGDPLayer) ama2007a)
					.generalInformationLayer();

			ama2008a = new AmazonGDPLayer(dataR.getMuniData(), "2008");
			anoLayer2008a = ((AmazonGDPLayer) ama2008a).addAnnotations();
			generalAnoLayer2008a = ((AmazonGDPLayer) ama2008a)
					.generalInformationLayer();

			// insertBeforeBeforeCompass(this.getWwd(), ama2004);
			// insertBeforeBeforeCompass(this.getWwd(), anoLayer2004);
			// insertBeforeBeforeCompass(this.getWwd(), generalAnoLayer2004);
			//
			// insertBeforeBeforeCompass(this.getWwd(),
			// new CountryBoundariesLayer());

			// controller.flyToPosition(PARA_POS, INITIAL_ZOOM);

		}

		/**
		 * @author Umut Tas
		 */
		public void addAcumPoplayer(DataRetriever dataR) {

			ama2002b = new AmazonPopAcumLayer(dataR.getMuniData(), "2002");
			anoLayer2002b = ((AmazonPopAcumLayer) ama2002b).addAnnotations();
			generalAnoLayer2002b = ((AmazonPopAcumLayer) ama2002b)
					.generalInformationLayer();

			ama2003b = new AmazonPopAcumLayer(dataR.getMuniData(), "2003");
			anoLayer2003b = ((AmazonPopAcumLayer) ama2003b).addAnnotations();
			generalAnoLayer2003b = ((AmazonPopAcumLayer) ama2003b)
					.generalInformationLayer();

			ama2004b = new AmazonPopAcumLayer(dataR.getMuniData(), "2004");
			anoLayer2004b = ((AmazonPopAcumLayer) ama2004b).addAnnotations();
			generalAnoLayer2004b = ((AmazonPopAcumLayer) ama2004b)
					.generalInformationLayer();

			ama2005b = new AmazonPopAcumLayer(dataR.getMuniData(), "2005");
			anoLayer2005b = ((AmazonPopAcumLayer) ama2005b).addAnnotations();
			generalAnoLayer2005b = ((AmazonPopAcumLayer) ama2005b)
					.generalInformationLayer();

			ama2006b = new AmazonPopAcumLayer(dataR.getMuniData(), "2006");
			anoLayer2006b = ((AmazonPopAcumLayer) ama2006b).addAnnotations();
			generalAnoLayer2006b = ((AmazonPopAcumLayer) ama2006b)
					.generalInformationLayer();

			ama2007b = new AmazonPopAcumLayer(dataR.getMuniData(), "2007");
			anoLayer2007b = ((AmazonPopAcumLayer) ama2007b).addAnnotations();
			generalAnoLayer2007b = ((AmazonPopAcumLayer) ama2007b)
					.generalInformationLayer();

			ama2008b = new AmazonPopAcumLayer(dataR.getMuniData(), "2008");
			anoLayer2008b = ((AmazonPopAcumLayer) ama2008b).addAnnotations();
			generalAnoLayer2008b = ((AmazonPopAcumLayer) ama2008b)
					.generalInformationLayer();

			// insertBeforeBeforeCompass(this.getWwd(),
			// new CountryBoundariesLayer());
			// insertBeforeBeforeCompass(this.getWwd(), ama2002);
			// insertBeforeBeforeCompass(this.getWwd(), anoLayer2002);
			// insertBeforeBeforeCompass(this.getWwd(), generalAnoLayer2002);
			// controller.flyToPosition(PARA_POS, INITIAL_ZOOM);

		}

		public void refreshActiveLayers(RenderableLayer layer1,
				AnnotationLayer layer2, AnnotationLayer layer3) {
			activeLayers = new ArrayList();
			activeLayers.add(layer1);
			activeLayers.add(layer2);
			activeLayers.add(layer3);
		}

		public void changeLayers() {
			getWwd().getModel().getLayers().remove(activeLayers.get(0));
			getWwd().getModel().getLayers().remove(activeLayers.get(1));
			getWwd().getModel().getLayers().remove(activeLayers.get(2));
			activeLayers = new ArrayList();
			setLayerChanger(0);

			if (activeLayer == "a") {
				activeLayer = "b";
				getWwd().getModel().getLayers().add(ama2002b);
				getWwd().getModel().getLayers().add(anoLayer2002b);
				getWwd().getModel().getLayers().add(generalAnoLayer2002b);
				activeLayers.add(ama2002b);
				activeLayers.add(anoLayer2002b);
				activeLayers.add(generalAnoLayer2002b);
			} else if (activeLayer == "b") {
				activeLayer = "c";
				getWwd().getModel().getLayers().add(ama2004);
				getWwd().getModel().getLayers().add(anoLayer2004);
				getWwd().getModel().getLayers().add(generalAnoLayer2004);
				activeLayers.add(ama2004);
				activeLayers.add(anoLayer2004);
				activeLayers.add(generalAnoLayer2004);
			} else if (activeLayer == "c") {
				activeLayer = "a";
				getWwd().getModel().getLayers().add(ama2004a);
				getWwd().getModel().getLayers().add(anoLayer2004a);
				getWwd().getModel().getLayers().add(generalAnoLayer2004a);
				activeLayers.add(ama2004a);
				activeLayers.add(anoLayer2004a);
				activeLayers.add(generalAnoLayer2004a);
			}

		}

		/**
		 * @author Umut Tas
		 */
		public RenderableLayer getAma2002() {
			return ama2002;
		}

		/**
		 * @author Umut Tas
		 */
		public void setAma2002(RenderableLayer ama2002) {
			this.ama2002 = ama2002;
		}

		/**
		 * @author Umut Tas
		 */
		public RenderableLayer getAma2003() {
			return ama2003;
		}

		public void setAma2003(RenderableLayer ama2003) {
			this.ama2003 = ama2003;
		}

		/**
		 * @author Umut Tas
		 */
		public RenderableLayer getAma2004() {
			return ama2004;
		}

		/**
		 * @author Umut Tas
		 */
		public void setAma2004(RenderableLayer ama2004) {
			this.ama2004 = ama2004;
		}

		/**
		 * @author Umut Tas
		 */
		public RenderableLayer getAma2005() {
			return ama2005;
		}

		/**
		 * @author Umut Tas
		 */
		public void setAma2005(RenderableLayer ama2005) {
			this.ama2005 = ama2005;
		}

		/**
		 * @author Umut Tas
		 */
		public RenderableLayer getAma2006() {
			return ama2006;
		}

		/**
		 * @author Umut Tas
		 */
		public void setAma2006(RenderableLayer ama2006) {
			this.ama2006 = ama2006;
		}

		/**
		 * @author Umut Tas
		 */
		public RenderableLayer getAma2007() {
			return ama2007;
		}

		/**
		 * @author Umut Tas
		 */
		public void setAma2007(RenderableLayer ama2007) {
			this.ama2007 = ama2007;
		}

		/**
		 * @author Umut Tas
		 */
		public RenderableLayer getAma2008() {
			return ama2008;
		}

		/**
		 * @author Umut Tas
		 */
		public void setAma2008(RenderableLayer ama2008) {
			this.ama2008 = ama2008;
		}

		/**
		 * @author Umut Tas
		 */
		public AnnotationLayer getGeneralAnoLayer2002() {
			return generalAnoLayer2002;
		}

		/**
		 * @author Umut Tas
		 */
		public void setGeneralAnoLayer2002(AnnotationLayer generalAnoLayer2002) {
			this.generalAnoLayer2002 = generalAnoLayer2002;
		}

		/**
		 * @author Umut Tas
		 */
		public AnnotationLayer getGeneralAnoLayer2003() {
			return generalAnoLayer2003;
		}

		/**
		 * @author Umut Tas
		 */
		public void setGeneralAnoLayer2003(AnnotationLayer generalAnoLayer2003) {
			this.generalAnoLayer2003 = generalAnoLayer2003;
		}

		/**
		 * @author Umut Tas
		 */
		public AnnotationLayer getGeneralAnoLayer2004() {
			return generalAnoLayer2004;
		}

		/**
		 * @author Umut Tas
		 */
		public void setGeneralAnoLayer2004(AnnotationLayer generalAnoLayer2004) {
			this.generalAnoLayer2004 = generalAnoLayer2004;
		}

		/**
		 * @author Umut Tas
		 */
		public AnnotationLayer getGeneralAnoLayer2005() {
			return generalAnoLayer2005;
		}

		/**
		 * @author Umut Tas
		 */
		public void setGeneralAnoLayer2005(AnnotationLayer generalAnoLayer2005) {
			this.generalAnoLayer2005 = generalAnoLayer2005;
		}

		/**
		 * @author Umut Tas
		 */
		public AnnotationLayer getGeneralAnoLayer2006() {
			return generalAnoLayer2006;
		}

		/**
		 * @author Umut Tas
		 */
		public void setGeneralAnoLayer2006(AnnotationLayer generalAnoLayer2006) {
			this.generalAnoLayer2006 = generalAnoLayer2006;
		}

		/**
		 * @author Umut Tas
		 */
		public AnnotationLayer getGeneralAnoLayer2007() {
			return generalAnoLayer2007;
		}

		/**
		 * @author Umut Tas
		 */
		public void setGeneralAnoLayer2007(AnnotationLayer generalAnoLayer2007) {
			this.generalAnoLayer2007 = generalAnoLayer2007;
		}

		/**
		 * @author Umut Tas
		 */
		public AnnotationLayer getGeneralAnoLayer2008() {
			return generalAnoLayer2008;
		}

		/**
		 * @author Umut Tas
		 */
		public void setGeneralAnoLayer2008(AnnotationLayer generalAnoLayer2008) {
			this.generalAnoLayer2008 = generalAnoLayer2008;
		}

		/**
		 * @author Umut Tas
		 */
		public AnnotationLayer getAnoLayer2002() {
			return anoLayer2002;
		}

		/**
		 * @author Umut Tas
		 */
		public void setAnoLayer2002(AnnotationLayer anoLayer2002) {
			this.anoLayer2002 = anoLayer2002;
		}

		/**
		 * @author Umut Tas
		 */
		public AnnotationLayer getAnoLayer2003() {
			return anoLayer2003;
		}

		/**
		 * @author Umut Tas
		 */
		public void setAnoLayer2003(AnnotationLayer anoLayer2003) {
			this.anoLayer2003 = anoLayer2003;
		}

		/**
		 * @author Umut Tas
		 */
		public AnnotationLayer getAnoLayer2004() {
			return anoLayer2004;
		}

		/**
		 * @author Umut Tas
		 */
		public void setAnoLayer2004(AnnotationLayer anoLayer2004) {
			this.anoLayer2004 = anoLayer2004;
		}

		/**
		 * @author Umut Tas
		 */
		public AnnotationLayer getAnoLayer2005() {
			return anoLayer2005;
		}

		/**
		 * @author Umut Tas
		 */
		public void setAnoLayer2005(AnnotationLayer anoLayer2005) {
			this.anoLayer2005 = anoLayer2005;
		}

		/**
		 * @author Umut Tas
		 */
		public AnnotationLayer getAnoLayer2006() {
			return anoLayer2006;
		}

		/**
		 * @author Umut Tas
		 */
		public void setAnoLayer2006(AnnotationLayer anoLayer2006) {
			this.anoLayer2006 = anoLayer2006;
		}

		/**
		 * @author Umut Tas
		 */
		public AnnotationLayer getAnoLayer2007() {
			return anoLayer2007;
		}

		/**
		 * @author Umut Tas
		 */
		public void setAnoLayer2007(AnnotationLayer anoLayer2007) {
			this.anoLayer2007 = anoLayer2007;
		}

		/**
		 * @author Umut Tas
		 */
		public AnnotationLayer getAnoLayer2008() {
			return anoLayer2008;
		}

		/**
		 * @author Umut Tas
		 */
		public void setAnoLayer2008(AnnotationLayer anoLayer2008) {
			this.anoLayer2008 = anoLayer2008;
		}

		/**
		 * @author Umut Tas
		 */
		public int getLayerChanger() {
			return layerChanger;
		}

		public void setLayerChanger(int layerChanger) {
			this.layerChanger = layerChanger;
		}

		public WorldWindowGLCanvas getWwd() {
			return wwd;
		}

		/**
		 * @author Umut Tas
		 */
		public boolean isYear2002() {
			return year2002;
		}

		/**
		 * @author Umut Tas
		 */
		public void setYear2002(boolean year2002) {
			this.year2002 = year2002;
		}

		public AppFrameController getController() {
			return controller;
		}

		public RenderableLayer getAma2002a() {
			return ama2002a;
		}

		public void setAma2002a(RenderableLayer ama2002a) {
			this.ama2002a = ama2002a;
		}

		public RenderableLayer getAma2003a() {
			return ama2003a;
		}

		public void setAma2003a(RenderableLayer ama2003a) {
			this.ama2003a = ama2003a;
		}

		public RenderableLayer getAma2004a() {
			return ama2004a;
		}

		public void setAma2004a(RenderableLayer ama2004a) {
			this.ama2004a = ama2004a;
		}

		public RenderableLayer getAma2005a() {
			return ama2005a;
		}

		public void setAma2005a(RenderableLayer ama2005a) {
			this.ama2005a = ama2005a;
		}

		public RenderableLayer getAma2006a() {
			return ama2006a;
		}

		public void setAma2006a(RenderableLayer ama2006a) {
			this.ama2006a = ama2006a;
		}

		public RenderableLayer getAma2007a() {
			return ama2007a;
		}

		public void setAma2007a(RenderableLayer ama2007a) {
			this.ama2007a = ama2007a;
		}

		public RenderableLayer getAma2008a() {
			return ama2008a;
		}

		public void setAma2008a(RenderableLayer ama2008a) {
			this.ama2008a = ama2008a;
		}

		public RenderableLayer getAma2002b() {
			return ama2002b;
		}

		public void setAma2002b(RenderableLayer ama2002b) {
			this.ama2002b = ama2002b;
		}

		public RenderableLayer getAma2003b() {
			return ama2003b;
		}

		public void setAma2003b(RenderableLayer ama2003b) {
			this.ama2003b = ama2003b;
		}

		public RenderableLayer getAma2004b() {
			return ama2004b;
		}

		public void setAma2004b(RenderableLayer ama2004b) {
			this.ama2004b = ama2004b;
		}

		public RenderableLayer getAma2005b() {
			return ama2005b;
		}

		public void setAma2005b(RenderableLayer ama2005b) {
			this.ama2005b = ama2005b;
		}

		public RenderableLayer getAma2006b() {
			return ama2006b;
		}

		public void setAma2006b(RenderableLayer ama2006b) {
			this.ama2006b = ama2006b;
		}

		public RenderableLayer getAma2007b() {
			return ama2007b;
		}

		public void setAma2007b(RenderableLayer ama2007b) {
			this.ama2007b = ama2007b;
		}

		public RenderableLayer getAma2008b() {
			return ama2008b;
		}

		public void setAma2008b(RenderableLayer ama2008b) {
			this.ama2008b = ama2008b;
		}

		public AnnotationLayer getAnoLayer2002a() {
			return anoLayer2002a;
		}

		public void setAnoLayer2002a(AnnotationLayer anoLayer2002a) {
			this.anoLayer2002a = anoLayer2002a;
		}

		public AnnotationLayer getAnoLayer2003a() {
			return anoLayer2003a;
		}

		public void setAnoLayer2003a(AnnotationLayer anoLayer2003a) {
			this.anoLayer2003a = anoLayer2003a;
		}

		public AnnotationLayer getAnoLayer2004a() {
			return anoLayer2004a;
		}

		public void setAnoLayer2004a(AnnotationLayer anoLayer2004a) {
			this.anoLayer2004a = anoLayer2004a;
		}

		public AnnotationLayer getAnoLayer2005a() {
			return anoLayer2005a;
		}

		public void setAnoLayer2005a(AnnotationLayer anoLayer2005a) {
			this.anoLayer2005a = anoLayer2005a;
		}

		public AnnotationLayer getAnoLayer2006a() {
			return anoLayer2006a;
		}

		public void setAnoLayer2006a(AnnotationLayer anoLayer2006a) {
			this.anoLayer2006a = anoLayer2006a;
		}

		public AnnotationLayer getAnoLayer2007a() {
			return anoLayer2007a;
		}

		public void setAnoLayer2007a(AnnotationLayer anoLayer2007a) {
			this.anoLayer2007a = anoLayer2007a;
		}

		public AnnotationLayer getAnoLayer2008a() {
			return anoLayer2008a;
		}

		public void setAnoLayer2008a(AnnotationLayer anoLayer2008a) {
			this.anoLayer2008a = anoLayer2008a;
		}

		public AnnotationLayer getAnoLayer2002b() {
			return anoLayer2002b;
		}

		public void setAnoLayer2002b(AnnotationLayer anoLayer2002b) {
			this.anoLayer2002b = anoLayer2002b;
		}

		public AnnotationLayer getAnoLayer2003b() {
			return anoLayer2003b;
		}

		public void setAnoLayer2003b(AnnotationLayer anoLayer2003b) {
			this.anoLayer2003b = anoLayer2003b;
		}

		public AnnotationLayer getAnoLayer2004b() {
			return anoLayer2004b;
		}

		public void setAnoLayer2004b(AnnotationLayer anoLayer2004b) {
			this.anoLayer2004b = anoLayer2004b;
		}

		public AnnotationLayer getAnoLayer2005b() {
			return anoLayer2005b;
		}

		public void setAnoLayer2005b(AnnotationLayer anoLayer2005b) {
			this.anoLayer2005b = anoLayer2005b;
		}

		public AnnotationLayer getAnoLayer2006b() {
			return anoLayer2006b;
		}

		public void setAnoLayer2006b(AnnotationLayer anoLayer2006b) {
			this.anoLayer2006b = anoLayer2006b;
		}

		public AnnotationLayer getAnoLayer2007b() {
			return anoLayer2007b;
		}

		public void setAnoLayer2007b(AnnotationLayer anoLayer2007b) {
			this.anoLayer2007b = anoLayer2007b;
		}

		public AnnotationLayer getAnoLayer2008b() {
			return anoLayer2008b;
		}

		public void setAnoLayer2008b(AnnotationLayer anoLayer2008b) {
			this.anoLayer2008b = anoLayer2008b;
		}

		public AnnotationLayer getGeneralAnoLayer2002a() {
			return generalAnoLayer2002a;
		}

		public void setGeneralAnoLayer2002a(AnnotationLayer generalAnoLayer2002a) {
			this.generalAnoLayer2002a = generalAnoLayer2002a;
		}

		public AnnotationLayer getGeneralAnoLayer2003a() {
			return generalAnoLayer2003a;
		}

		public void setGeneralAnoLayer2003a(AnnotationLayer generalAnoLayer2003a) {
			this.generalAnoLayer2003a = generalAnoLayer2003a;
		}

		public AnnotationLayer getGeneralAnoLayer2004a() {
			return generalAnoLayer2004a;
		}

		public void setGeneralAnoLayer2004a(AnnotationLayer generalAnoLayer2004a) {
			this.generalAnoLayer2004a = generalAnoLayer2004a;
		}

		public AnnotationLayer getGeneralAnoLayer2005a() {
			return generalAnoLayer2005a;
		}

		public void setGeneralAnoLayer2005a(AnnotationLayer generalAnoLayer2005a) {
			this.generalAnoLayer2005a = generalAnoLayer2005a;
		}

		public AnnotationLayer getGeneralAnoLayer2006a() {
			return generalAnoLayer2006a;
		}

		public void setGeneralAnoLayer2006a(AnnotationLayer generalAnoLayer2006a) {
			this.generalAnoLayer2006a = generalAnoLayer2006a;
		}

		public AnnotationLayer getGeneralAnoLayer2007a() {
			return generalAnoLayer2007a;
		}

		public void setGeneralAnoLayer2007a(AnnotationLayer generalAnoLayer2007a) {
			this.generalAnoLayer2007a = generalAnoLayer2007a;
		}

		public AnnotationLayer getGeneralAnoLayer2008a() {
			return generalAnoLayer2008a;
		}

		public void setGeneralAnoLayer2008a(AnnotationLayer generalAnoLayer2008a) {
			this.generalAnoLayer2008a = generalAnoLayer2008a;
		}

		public AnnotationLayer getGeneralAnoLayer2002b() {
			return generalAnoLayer2002b;
		}

		public void setGeneralAnoLayer2002b(AnnotationLayer generalAnoLayer2002b) {
			this.generalAnoLayer2002b = generalAnoLayer2002b;
		}

		public AnnotationLayer getGeneralAnoLayer2003b() {
			return generalAnoLayer2003b;
		}

		public void setGeneralAnoLayer2003b(AnnotationLayer generalAnoLayer2003b) {
			this.generalAnoLayer2003b = generalAnoLayer2003b;
		}

		public AnnotationLayer getGeneralAnoLayer2004b() {
			return generalAnoLayer2004b;
		}

		public void setGeneralAnoLayer2004b(AnnotationLayer generalAnoLayer2004b) {
			this.generalAnoLayer2004b = generalAnoLayer2004b;
		}

		public AnnotationLayer getGeneralAnoLayer2005b() {
			return generalAnoLayer2005b;
		}

		public void setGeneralAnoLayer2005b(AnnotationLayer generalAnoLayer2005b) {
			this.generalAnoLayer2005b = generalAnoLayer2005b;
		}

		public AnnotationLayer getGeneralAnoLayer2006b() {
			return generalAnoLayer2006b;
		}

		public void setGeneralAnoLayer2006b(AnnotationLayer generalAnoLayer2006b) {
			this.generalAnoLayer2006b = generalAnoLayer2006b;
		}

		public AnnotationLayer getGeneralAnoLayer2007b() {
			return generalAnoLayer2007b;
		}

		public void setGeneralAnoLayer2007b(AnnotationLayer generalAnoLayer2007b) {
			this.generalAnoLayer2007b = generalAnoLayer2007b;
		}

		public AnnotationLayer getGeneralAnoLayer2008b() {
			return generalAnoLayer2008b;
		}

		public void setGeneralAnoLayer2008b(AnnotationLayer generalAnoLayer2008b) {
			this.generalAnoLayer2008b = generalAnoLayer2008b;
		}

		public String getActiveLayer() {
			return activeLayer;
		}

		public void setActiveLayer(String activeLayer) {
			this.activeLayer = activeLayer;
		}

	}

	public static void removeCompass(WorldWindow wwd) {
		// Insert the layer into the layer list just before the compass.
		int compassPosition = 0;
		LayerList layers = wwd.getModel().getLayers();
		for (Layer l : layers) {
			if (l instanceof CompassLayer)
				compassPosition = layers.indexOf(l);
		}
		layers.remove(compassPosition);
	}

	public static void insertBeforeCompass(WorldWindow wwd, Layer layer) {
		// Insert the layer into the layer list just before the compass.
		int compassPosition = 0;
		LayerList layers = wwd.getModel().getLayers();
		for (Layer l : layers) {
			if (l instanceof CompassLayer)
				compassPosition = layers.indexOf(l);
		}
		layers.add(compassPosition, layer);
	}

	public static void insertBeforeBeforeCompass(WorldWindow wwd, Layer layer) {
		// Insert the layer into the layer list just before the compass.
		int compassPosition = 0;
		LayerList layers = wwd.getModel().getLayers();
		for (Layer l : layers) {
			if (l instanceof CompassLayer)
				compassPosition = layers.indexOf(l);
		}
		layers.add(compassPosition - 1, layer);
	}

	public static void main(String[] args) {
		AppFrame app = new AppFrame();
		app.setVisible(true);

	}
}
