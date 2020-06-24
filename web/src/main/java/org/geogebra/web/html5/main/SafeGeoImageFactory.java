package org.geogebra.web.html5.main;

import org.geogebra.common.kernel.Construction;
import org.geogebra.common.kernel.commands.AlgebraProcessor;
import org.geogebra.common.kernel.geos.GeoImage;
import org.geogebra.common.kernel.geos.GeoPoint;
import org.geogebra.common.kernel.kernelND.GeoPointND;
import org.geogebra.common.util.ImageManager;
import org.geogebra.web.html5.safeimage.ImageFile;
import org.geogebra.web.html5.safeimage.SafeImage;
import org.geogebra.web.html5.safeimage.SafeImageProvider;
import org.geogebra.web.html5.util.ImageLoadCallback;
import org.geogebra.web.html5.util.ImageManagerW;
import org.geogebra.web.html5.util.ImageWrapper;

public class SafeGeoImageFactory implements SafeImageProvider, ImageLoadCallback {
	private final AppW app;
	private final Construction construction;
	private final AlgebraProcessor algebraProcessor;
	private final ImageManagerW imageManager;
	private final GeoImage geoImage;
	private ImageFile imageFile;
	private ImageWrapper wrapper;
	private boolean autoCorners;
	private String cornerLabel1 = null;
	private String cornerLabel2 = null;
	private String cornerLabel4 = null;

	public SafeGeoImageFactory(AppW app) {
		this.app = app;
		construction = app.getKernel().getConstruction();
		algebraProcessor = app.getKernel().getAlgebraProcessor();
		imageManager = app.getImageManager();
		autoCorners = true;
		geoImage = new GeoImage(construction);
	}

	public GeoImage create(String fileName, String content) {
		ImageFile imageFile = new ImageFile(fileName, content);
		SafeImage safeImage = new SafeImage(imageFile, this);
		safeImage.process();
		return geoImage;
	}

	@Override
	public void onReady(ImageFile imageFile) {
		this.imageFile = imageFile;
		imageManager.addExternalImage(imageFile.getFileName(),
				imageFile.getContent());
		imageManager.triggerSingleImageLoading(imageFile.getFileName(),
				geoImage);
		wrapper = new ImageWrapper(
				imageManager.getExternalImage(imageFile.getFileName(), app, true));
		wrapper.attachNativeLoadHandler(imageManager,this);
	}

	@Override
	public void onLoad() {
		geoImage.setImageFileName(imageFile.getFileName(),
				wrapper.getElement().getWidth(),
				wrapper.getElement().getHeight());

		if (autoCorners) {
			app.getGuiManager().setImageCornersFromSelection(geoImage);
		} else {
			setManualCorners();
		}


		if (imageManager.isPreventAuxImage()) {
			geoImage.setAuxiliaryObject(false);
		}
		if (app.isWhiteboardActive()) {
			app.getActiveEuclidianView().getEuclidianController()
					.selectAndShowSelectionUI(geoImage);
		}
		app.setDefaultCursor();
		app.storeUndoInfo();
	}

	private void setManualCorners() {
		if (cornerLabel1 != null) {

			GeoPointND corner1 = algebraProcessor
					.evaluateToPoint(cornerLabel1, null, true);
			geoImage.setCorner(corner1, 0);

			GeoPoint corner2;
			if (cornerLabel2 != null) {
				corner2 = (GeoPoint) algebraProcessor
						.evaluateToPoint(cornerLabel2, null, true);
			} else {
				corner2 = new GeoPoint(construction, 0, 0, 1);
				geoImage.calculateCornerPoint(corner2,
						2);
			}
			geoImage.setCorner(corner2, 1);

			// make sure 2nd corner is on screen
			ImageManager.ensure2ndCornerOnScreen(
					corner1.getInhomX(), corner2, app);

			if (cornerLabel4 != null) {
				GeoPointND corner4 = algebraProcessor
						.evaluateToPoint(cornerLabel4, null, true);
				geoImage.setCorner(corner4, 2);
			}
		}
	}

	public SafeGeoImageFactory withAutoCorners(boolean autoCorners) {
		this.autoCorners = autoCorners;
		return this;
	}


	public SafeGeoImageFactory withCorners(String cornerLabel1, String cornerLabel2,
			String cornerLabel4) {
		this.cornerLabel1 = cornerLabel1;
		this.cornerLabel2 = cornerLabel2;
		this.cornerLabel4 = cornerLabel4;
		return this;
	}
}