# TextureView



 TextureView 是 View 层次结构的固有成员，所以其行为与其他所有 View 一样，可以与其他元素相互叠加。您可以执行任意转换，并通过简单的 API 调用将内容检索为位图。



使用 TextureView 时，View 合成往往使用 GLES 执行，并且对其内容进行的更新也可能会导致其他 View 元素重绘（例如，如果它们位于 TextureView 上方）。View 呈现完成后，应用界面层必须由 SurfaceFlinger 与其他分层合成，以便您可以高效地将每个可见像素合成两次。对于全屏视频播放器，或任何其他相当于位于视频上方的界面元素的应用，SurfaceView 可以带来更好的效果。



```java
TextureView textureView.setSurfaceTextureListener(this);


    @Override
    public void onSurfaceTextureAvailable(final SurfaceTexture surface, final int width, final int height) {
        initPlayer(surface);
        //do sth
    }

    @Override
    public void onSurfaceTextureSizeChanged(final SurfaceTexture surface, final int width, final int height) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(final SurfaceTexture surface) {
        return false;
    }

    @Override
    public void onSurfaceTextureUpdated(final SurfaceTexture surface) {

    }

initPlayer(surface){
  player.setSurface(new Surface(surfaceTexture));
}
```

