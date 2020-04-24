package org.jetbrains.skija.examples.lwjgl;

import java.util.stream.IntStream;
import org.jetbrains.skija.*;

public class EffectsScene implements Scene {
    @Override
    public void draw(Canvas canvas, int width, int height, float dpi, int xpos, int ypos) {
        canvas.translate(20, 20);
        drawShadowsBlurs(canvas);
        drawImageFilters(canvas, width, dpi); 
        drawLights(canvas);
        drawBlends(canvas);
        drawShaders(canvas);
        drawGradients(canvas);
        drawColorSpace(canvas);
    }

    private void drawShadowsBlurs(Canvas canvas) {
        canvas.save();
        try (Paint fill = new Paint().setColor(0xFF8E86C9);
             Path path = new Path())
        {
            path.setFillType(Path.FillType.EVEN_ODD);
            path.lineTo(0, 60).lineTo(60, 60).lineTo(60, 0).closePath();
            path.moveTo(10, 5).lineTo(55, 10).lineTo(50, 55).lineTo(5, 50).closePath();

            ImageFilter[] filters = new ImageFilter[] {
                ImageFilter.dropShadow(0, 0, 10, 10, 0xFF000000),
                ImageFilter.dropShadow(2, 2, 0, 0, 0xFF000000),
                ImageFilter.dropShadow(0, 0, 10, 10, 0xFFF42372),
                ImageFilter.dropShadowOnly(0, 0, 2, 2, 0xFFCC3333),
                ImageFilter.dropShadow(0, 0, 2, 2, 0xFFCC3333, null, IRect.makeXYWH(30, 30, 30, 30)),
                ImageFilter.dropShadow(2, 2, 2, 2, 0xFF3333CC, ImageFilter.dropShadow(-2, -2, 2, 2, 0xFFCC3333), null),
                ImageFilter.blur(2, 2, TileMode.CLAMP),
                ImageFilter.blur(2, 2, TileMode.REPEAT),
                ImageFilter.blur(2, 2, TileMode.MIRROR),
                ImageFilter.blur(2, 2, TileMode.DECAL),
            };

            for (var filter: filters) {
                fill.setImageFilter(filter);
                canvas.drawPath(path, fill);
                canvas.translate(70, 0);
                filter.close();
            }
        }
        canvas.restore();
        canvas.translate(0, 70);
    }

    private void drawImageFilters(Canvas canvas, float width, float dpi) {
        canvas.save();
        try (Paint fill = new Paint().setColor(0xFFFF9F1B);
             Path path = new Path())
        {
            path.setFillType(Path.FillType.EVEN_ODD);
            path.moveTo(10, 10).rMoveTo(20, 1.6f).rLineTo(11.7f, 36.2f).rLineTo(-30.8f, -22.4f).rLineTo(38.1f, 0f).rLineTo(-30.8f, 22.4f);

            IRect bb = IRect.makeXYWH(0, 0, 60, 60);
            ImageFilter[] filters = new ImageFilter[] {
                ImageFilter.offset(0, 0, null, null),
                ImageFilter.magnifier(Rect.makeXYWH(0 * dpi, 0 * dpi, 60 * dpi, 60 * dpi), 5f, null, null),
                ImageFilter.magnifier(Rect.makeXYWH(0 * dpi, 0 * dpi, 60 * dpi, 60 * dpi), 10f, null, null),
                ImageFilter.magnifier(Rect.makeXYWH(0 * dpi, 0 * dpi, 60 * dpi, 60 * dpi), 20f, null, null),
                ImageFilter.offset(10, 10, null, null),
                ImageFilter.paint(fill, bb),
                ImageFilter.tile(Rect.makeXYWH(10, 10, 40, 40), Rect.makeXYWH(0, 0, 60, 60), null),
                ImageFilter.dilate(2, 2, null, null),
                ImageFilter.erode(2, 2, null, null),
            };

            for (var filter: filters) {
                fill.setImageFilter(filter);
                canvas.drawPath(path, fill);
                canvas.translate(70, 0);
                filter.close();
            }
        }
        canvas.restore();
        canvas.translate(0, 70);
    }

    private void drawLights(Canvas canvas) {
        canvas.save();
        try (Paint fill = new Paint().setColor(0xFFFF9F1B);
             Path path = new Path())
        {
            path.setFillType(Path.FillType.EVEN_ODD);
            path.moveTo(10, 10).rMoveTo(20, 1.6f).rLineTo(11.7f, 36.2f).rLineTo(-30.8f, -22.4f).rLineTo(38.1f, 0f).rLineTo(-30.8f, 22.4f);

            IRect bb = IRect.makeXYWH(0, 0, 60, 60);
            ImageFilter[] filters = new ImageFilter[] {
                ImageFilter.distantLitDiffuse( 0,  1, 1, 0xFFFF9F1B, 1, 0.5f, null, bb),
                ImageFilter.distantLitDiffuse( 0, -1, 1, 0xFFFF9F1B, 1, 0.5f, null, bb),
                ImageFilter.distantLitDiffuse( 1,  0, 1, 0xFFFF9F1B, 1, 0.5f, null, bb),
                ImageFilter.distantLitDiffuse(-1,  0, 1, 0xFFFF9F1B, 1, 0.5f, null, bb),
                ImageFilter.distantLitDiffuse(-1, -1, 1, 0xFFFF9F1B, 1, 0.5f, null, bb),
                ImageFilter.pointLitDiffuse(0, 0, 30, 0xFFFF9F1B, 1, 0.5f, null, bb),
                ImageFilter.spotLitDiffuse(0, 0, 30, 30, 30, 0, 1f, 30, 0xFFFF9F1B, 1, 0.5f, null, bb),
                ImageFilter.distantLitSpecular(-1, -1, 1, 0xFFFF9F1B, 1, 1.1f, 1.1f, null, bb),
                ImageFilter.pointLitSpecular(0, 0, 30, 0xFFFF9F1B, 1, 1.1f, 1.1f, null, bb),
                ImageFilter.spotLitSpecular(0, 0, 30, 30, 30, 0, 1f, 30, 0xFFFF9F1B, 1, 1.1f, 1.1f, null, bb),
            };

            for (var filter: filters) {
                fill.setImageFilter(filter);
                canvas.drawPath(path, fill);
                canvas.translate(70, 0);
                filter.close();
            }
        }
        canvas.restore();
        canvas.translate(0, 70);
    }


    private void drawBlends(Canvas canvas) {
        canvas.save();
        try (Paint dst = new Paint().setColor(0xFFD62828);
             Paint src = new Paint().setColor(0xFF01D6A0);) {
            for (var blendMode: BlendMode.values()) {
                canvas.drawRect(Rect.makeXYWH(0, 0, 20, 20), dst);
                src.setBlendMode(blendMode);
                canvas.drawRect(Rect.makeXYWH(10, 10, 20, 20), src);
                canvas.translate(40, 0);
            }
        }
        canvas.restore();
        canvas.translate(0, 40);
    }

    private void drawShaders(Canvas canvas) {
        canvas.save();

        float percent = Math.abs((System.currentTimeMillis() % 3000) / 10f - 150f) - 25f;
        percent = Math.round(Math.max(0f, Math.min(100f, percent)));

        Shader[] shaders = new Shader[] {
            Shader.makeEmpty(),
            Shader.makeColor(0xFF247ba0),
            Shader.makeColor(new Color4f(0.5f, 0.5f, 0.5f), ColorSpace.SRGB_LINEAR),
            Shader.makeLerp(percent / 100f, Shader.makeColor(0xFFFF0000), Shader.makeColor(0xFF00FF00)),
            Shader.makeBlend(BlendMode.SRC_OVER, Shader.makeColor(0xFFFF0000), Shader.makeColor(0x9000FF00)),
            Shader.makeBlend(BlendMode.SCREEN, Shader.makeColor(0xFFFF0000), Shader.makeColor(0x9000FF00)),
            Shader.makeBlend(BlendMode.OVERLAY, Shader.makeColor(0xFFFF0000), Shader.makeColor(0x9000FF00)),
            Shader.makeBlend(BlendMode.DARKEN, Shader.makeColor(0xFFFF0000), Shader.makeColor(0x9000FF00)),
            Shader.makeBlend(BlendMode.LIGHTEN, Shader.makeColor(0xFFFF0000), Shader.makeColor(0x9000FF00)),
        };

        try (Paint fill = new Paint()) {
            for (Shader sh: shaders) {
                fill.setShader(sh);
                canvas.drawRect(Rect.makeXYWH(0, 0, 60, 60), fill);
                canvas.translate(70, 0);
                sh.close();
            }
        }

        canvas.restore();
        canvas.translate(0, 70);
    }

    private void drawGradients(Canvas canvas) {
        canvas.save();

        Shader[] shaders = new Shader[] {
            Shader.makeLinearGradient( 0,  0, 60,  0, new int[] { 0xFF247ba0, 0xFFf3ffbd }),
            Shader.makeLinearGradient(20,  0, 40,  0, new int[] { 0xFF247ba0, 0xFFf3ffbd }),
            Shader.makeLinearGradient(20,  0, 40,  0, new int[] { 0xFF247ba0, 0xFFf3ffbd }, null, TileMode.REPEAT),
            Shader.makeLinearGradient(20,  0, 40,  0, new int[] { 0xFF247ba0, 0xFFf3ffbd }, null, TileMode.MIRROR),
            Shader.makeLinearGradient(20,  0, 40,  0, new int[] { 0xFF247ba0, 0xFFf3ffbd }, null, TileMode.DECAL),
            Shader.makeLinearGradient( 0,  0,  0, 60, new int[] { 0xFF247ba0, 0xFFf3ffbd }),
            Shader.makeLinearGradient( 0,  0, 60, 60, new int[] { 0xFF247ba0, 0xFFf3ffbd }),
            Shader.makeLinearGradient( 0,  0, 60,  0, new int[] { 0xFF000000, 0x00000000 }),
            Shader.makeLinearGradient( 0,  0, 60,  0, new int[] { 0xFF247ba0, 0xFFff1654, 0xFF70c1b3, 0xFFf3ffbd, 0xFFb2dbbf }),
            Shader.makeLinearGradient( 0,  0, 60,  0, new int[] { 0xFF247ba0, 0xFFff1654, 0xFF70c1b3, 0xFFf3ffbd, 0xFFb2dbbf }, new float[] {0f, 0.1f, 0.2f, 0.9f, 1f}),

            Shader.makeRadialGradient(30, 30, 30, new int[] { 0xFF247ba0, 0xFFf3ffbd }),
            Shader.makeRadialGradient(30, 30, 10, new int[] { 0xFF247ba0, 0xFFf3ffbd }),
            Shader.makeRadialGradient(30, 30, 10, new int[] { 0xFF247ba0, 0xFFf3ffbd }, null, TileMode.REPEAT),

            Shader.makeTwoPointConicalGradient(20, 20, 10, 40, 40, 40, new int[] { 0xFF247ba0, 0xFFf3ffbd }),

            Shader.makeSweepGradient(30, 30, new int[] { 0xFF247ba0, 0xFFf3ffbd }),
            Shader.makeSweepGradient(30, 30, 45, 315, new int[] { 0xFF247ba0, 0xFFff1654, 0xFF70c1b3, 0xFFf3ffbd, 0xFFb2dbbf }, null, TileMode.DECAL),
        };
            
        try (Paint fill = new Paint()) {
            for (Shader sh: shaders) {
                fill.setShader(sh);
                canvas.drawRect(Rect.makeXYWH(0, 0, 60, 60), fill);
                canvas.translate(70, 0);
                sh.close();
            }
        }

        try (var sh = Shader.makeSweepGradient(30, 30, new int[] { 0xFFFF00FF, 0xFF00FFFF, 0xFFFFFF00, 0xFFFF00FF });
             var stroke = new Paint().setShader(sh).setStyle(Paint.Style.STROKE).setStrokeWidth(10))
        {
            canvas.drawCircle(30, 30, 30, stroke);
            canvas.translate(70, 0);
        }

        try (Path p1 = new Path().lineTo(30.1f, 0).lineTo(0, 32.5f).closePath();
             Shader s1 = Shader.makeLinearGradient(0, 32.5f, 30.1f, 0, 
                new int[] {0xFF0095D5, 0xFF3C83DC, 0xFF6D74E1, 0xFF806EE3},
                new float[] {0.1183f, 0.4178f, 0.6962f, 0.8333f});
             Paint f1 = new Paint().setShader(s1);

             Path p2 = new Path().moveTo(30.1f, 0).lineTo(0, 31.7f).lineTo(0, 60).lineTo(30.1f, 29.9f).lineTo(60, 0).closePath();
             Shader s2 = Shader.makeLinearGradient(0, 60, 60, 0,
                new int[] {0xFFC757BC, 0xFFD0609A, 0xFFE1725C, 0xFFEE7E2F, 0xFFF58613, 0xFFF88909},
                new float[] {0.1075f, 0.2138f, 0.4254f, 0.6048f, 0.743f, 0.8232f});
             Paint f2 = new Paint().setShader(s2);

             Path p3 = new Path().moveTo(0, 60).lineTo(30.1f, 29.9f).lineTo(60, 60).closePath();
             Shader s3 = Shader.makeLinearGradient(0, 60, 30.1f, 29.9f,
                new int[] { 0xFF0095D5, 0xFF238AD9, 0xFF557BDE, 0xFF7472E2, 0xFF806EE3 },
                new float[] {0f, 0.3f, 0.62f, 0.8643f, 1f});
             Paint f3 = new Paint().setShader(s3);)
        {
            canvas.drawPath(p1, f1);
            canvas.drawPath(p2, f2);
            canvas.drawPath(p3, f3);
            canvas.translate(70, 0);
        }

        canvas.restore();
        canvas.translate(0, 70);
    }
    
    private Shader linearCS(float x0, float y0, float x1, float y1, int colorFrom, int colorTo, ColorSpace cs, int steps) {
        Color4f from = new Color4f(colorFrom);
        Color4f to = new Color4f(colorTo);
        Color4f[] colors = new Color4f[steps + 1];
        float[] pos = new float[steps + 1];
        for (int i = 0; i < steps + 1; ++i) {
            pos[i] = (float) i / steps;
            colors[i] = new Color4f(from.r * (1 - pos[i]) + to.r * pos[i],
                                    from.g * (1 - pos[i]) + to.g * pos[i],
                                    from.b * (1 - pos[i]) + to.b * pos[i],
                                    from.a * (1 - pos[i]) + to.a * pos[i]);
        }
        return Shader.makeLinearGradient(x0, y0, x1, y1, colors, cs, pos, TileMode.CLAMP);
    } 

    private void drawColorSpace(Canvas canvas) {
        for (ColorSpace cs: new ColorSpace[] {null, ColorSpace.SRGB, ColorSpace.SRGB_LINEAR}) {
            canvas.save();

            Shader[] shaders = new Shader[] {
                linearCS(0, 0, 130, 0, 0xFFFF0000, 0xFF00FF00, cs, 10),
                linearCS(0, 0, 130, 0, 0xFFFF0000, 0xFF0000FF, cs, 10),
                linearCS(0, 0, 130, 0, 0xFFFF0000, 0xFF00FFFF, cs, 10),
                linearCS(0, 0, 130, 0, 0xFF0000FF, 0xFF00FF00, cs, 10),
                linearCS(0, 0, 130, 0, 0xFF00FFFF, 0xFFFF00FF, cs, 10),
                linearCS(0, 0, 130, 0, 0xFF00FF00, 0xFFFF00FF, cs, 10),
                linearCS(0, 0, 130, 0, 0xFF000000, 0xFFFFFFFF, cs, 10),
            };
                
            try (Paint fill = new Paint()) {
                for (Shader sh: shaders) {
                    fill.setShader(sh);
                    canvas.drawRect(Rect.makeXYWH(0, 0, 130, 30), fill);
                    canvas.translate(140, 0);
                    sh.close();
                }
            }

            canvas.restore();
            canvas.translate(0, 40);
        }
    }
}