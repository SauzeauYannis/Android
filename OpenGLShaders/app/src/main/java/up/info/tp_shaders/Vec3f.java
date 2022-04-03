package up.info.tp_shaders;

/**
 * Basic class to represent 3-vectors
 * Not intended to be a complete algebra class !
 *
 * @author Philippe Meseure
 * @version 1.0
 */
public class Vec3f {
    /**
     * x, y and z values of the current vector.
     * These are public to allow fast access and simple use.
     */
    public float x,
    /**
     * The Y.
     */
    y,
    /**
     * The Z.
     */
    z;

    /**
     * Default Constructor
     */
    public Vec3f() {
        this.x = this.y = this.z = 0.F;
    }

    /**
     * Constructor with initialisation
     *
     * @param x the x
     * @param y the y
     * @param z the z
     */
    public Vec3f(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Constructor by copy
     *
     * @param that vector to be copied in current vector
     */
    public Vec3f(final Vec3f that) {
        this.x = that.x;
        this.y = that.y;
        this.z = that.z;
    }

    /**
     * Set current vector's value to 0.0
     *
     * @return current vector
     */
    public Vec3f reset() {
        this.x = this.y = this.z = 0.F;
        return this;
    }

    /**
     * Copy "that" vector in current vector
     *
     * @param that vector to be copied
     * @return current vector
     */
    public Vec3f set(final Vec3f that) {
        this.x = that.x;
        this.y = that.y;
        this.z = that.z;
        return this;
    }

    /**
     * Copy x, y and z in current vector
     *
     * @param x the x
     * @param y the y
     * @param z the z
     * @return current vector
     */
    public Vec3f set(final float x, final float y, final float z) {
        this.x = x;
        this.y = y;
        this.z = z;
        return this;
    }

    /**
     * Length square float.
     *
     * @return square of the length of current vector
     */
    public float lengthSquare() {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    /**
     * Length float.
     *
     * @return length of current vector
     */
    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    /**
     * Normalize current vector
     *
     * @return current vector
     */
    public Vec3f normalize() {
        float l = this.lengthSquare();
        if (l == 0.F) return this;
        l = (float) Math.sqrt(l);
        return this.scale(1.F / l);
    }

    /**
     * Add a vector to current vector
     *
     * @param that any vector
     * @return current vector
     */
    public Vec3f add(final Vec3f that) {
        this.x += that.x;
        this.y += that.y;
        this.z += that.z;
        return this;
    }

    /**
     * Add two vectors v1 and v2 and put result into current vector
     *
     * @param v1 any vector
     * @param v2 any vector
     * @return current vector
     */
    public Vec3f setAdd(final Vec3f v1, final Vec3f v2) {
        this.x = v1.x + v2.x;
        this.y = v1.y + v2.y;
        this.z = v1.z + v2.z;
        return this;
    }

    /**
     * Subtract a vector to current vector
     *
     * @param that vector to subtract
     * @return current vector
     */
    public Vec3f sub(final Vec3f that) {
        this.x -= that.x;
        this.y -= that.y;
        this.z -= that.z;
        return this;
    }

    /**
     * Subtract two vectors and put result into current vector
     *
     * @param v1 any vector
     * @param v2 any vector
     * @return sub sub
     */
    public Vec3f setSub(final Vec3f v1, final Vec3f v2) {
        this.x = v1.x - v2.x;
        this.y = v1.y - v2.y;
        this.z = v1.z - v2.z;
        return this;
    }

    /**
     * Scale current vector uniformly
     *
     * @param scale uniform scale factor
     * @return current vector
     */
    public Vec3f scale(final float scale) {
        this.x *= scale;
        this.y *= scale;
        this.z *= scale;
        return this;
    }

    /**
     * Scale current vector with specific factors for each coordinate
     *
     * @param scalex scale factor for x
     * @param scaley scale factor for y
     * @param scalez scale factor for z
     * @return current vector
     */
    public Vec3f scale(final float scalex, final float scaley, final float scalez) {
        this.x *= scalex;
        this.y *= scaley;
        this.z *= scalez;
        return this;
    }

    /**
     * Scale a given vector by a uniform scale and put result into current vector
     *
     * @param scale scale factor
     * @param that  vector to scale
     * @return current vector
     */
    public Vec3f setScale(final float scale, final Vec3f that) {
        this.x = scale * that.x;
        this.y = scale * that.y;
        this.z = scale * that.z;
        return this;
    }

    /**
     * Scale a given vector by factors provided in another vector and put result into current vector
     *
     * @param v1 vector to scale
     * @param v2 scale factors for x, y and z
     * @return current vector
     */
    public Vec3f setScale(final Vec3f v1, final Vec3f v2) {
        this.x = v1.x * v2.x;
        this.y = v1.y * v2.y;
        this.z = v1.z * v2.z;
        return this;
    }

    /**
     * Add a given vector that is before-hand scaled, to the current vector
     *
     * @param scale scale factor
     * @param that  vector to scale and add to current vector
     * @return current vector
     */
    public Vec3f addScale(final float scale, final Vec3f that) {
        this.x += scale * that.x;
        this.y += scale * that.y;
        this.z += scale * that.z;
        return this;
    }

    /**
     * Multiply a given vector by a matrix and put result into current vector
     *
     * @param mat any matrix
     * @param v   any vector
     * @return current vector
     */
    public Vec3f setMatMultiply(final float[] mat, final Vec3f v) {
        this.x = mat[0] * v.x + mat[1] * v.y + mat[2] * v.z;
        this.y = mat[3] * v.x + mat[4] * v.y + mat[5] * v.z;
        this.z = mat[6] * v.x + mat[7] * v.y + mat[8] * v.z;
        return this;
    }

    /**
     * Multiply a given vector by the transpose of a matrix and put result into current vector
     *
     * @param mat any matrix
     * @param v   any vector
     * @return current vector
     */
    public Vec3f setTransposeMatMultiply(final float[] mat, final Vec3f v) {
        this.x = mat[0] * v.x + mat[3] * v.y + mat[6] * v.z;
        this.y = mat[1] * v.x + mat[4] * v.y + mat[7] * v.z;
        this.z = mat[2] * v.x + mat[5] * v.y + mat[8] * v.z;
        return this;
    }

    /**
     * Compute dot (inner) product with another vector
     *
     * @param v vector with which dotproduct is computed
     * @return result of dot product
     */
    public float dotProduct(final Vec3f v) {
        return this.x * v.x + this.y * v.y + this.z * v.z;
    }

    /**
     * Fill current vector with the cross product of two vectors.
     * Take care of parameters order, cross-product is anti-commutative!
     *
     * @param v1 First vector
     * @param v2 Second vector
     * @return current vector, filled with cross product v1*v2
     */
    public Vec3f setCrossProduct(final Vec3f v1, final Vec3f v2) {
        this.x = v1.y * v2.z - v1.z * v2.y;
        this.y = v1.z * v2.x - v1.x * v2.z; // take care of this value !!
        this.z = v1.x * v2.y - v1.y * v2.x;
        return this;
    }
}
