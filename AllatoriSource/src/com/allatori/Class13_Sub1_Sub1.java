package com.allatori;

import java.io.Serializable;

public class Class13_Sub1_Sub1 extends DateUtils implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public int anInt1339;
	public double aDouble1341;
	public static int anInt1342 = 899999963;
	public double aDouble1343;
	public static int anInt1344 = 54217137;
	public double aDouble1345;
	public int anInt1346;
	public double[] aDoubleArray1347;

	public void method368(int var1) {
		this.aDoubleArray1347 = new double[97];
		final int var2 = var1 / 30082;
		final int var3 = var1 - 30082 * var2;
		int var4 = var2 / 177 % 177 + 2;
		int var6 = var2 % 177 + 2;
		int var8 = var3 / 169 % 178 + 1;
		int var9 = var3 % 169;

		int var5;
		for (int var10000 = var5 = 0; var10000 < 97; var10000 = var5) {
			double var11 = 0.0D;
			double var13 = 0.5D;

			int var7;
			for (var10000 = var7 = 0; var10000 < 24; var10000 = var7) {
				final int var10 = var4 * var6 % 179 * var8 % 179;
				var4 = var6;
				var6 = var8;
				var8 = var10;
				if ((var9 = (53 * var9 + 1) % 169) * var10 % 64 >= 32) {
					var11 += var13;
				}

				var13 *= 0.5D;
				++var7;
			}

			this.aDoubleArray1347[var5] = var11;
			++var5;
		}

		this.aDouble1341 = 0.021602869033813477D;
		this.aDouble1345 = 0.45623308420181274D;
		this.aDouble1343 = 0.9999998211860657D;
		this.anInt1346 = 96;
		this.anInt1339 = 32;
	}

	public Class13_Sub1_Sub1() {
		this.method368(anInt1344);
	}

	public Class13_Sub1_Sub1(int var1) {
		this.method368(Math.abs(var1 % anInt1342));
	}

	@Override
	public final void method357(double[] var1, int var2) {
		int var5;
		for (int var10000 = var5 = 0; var10000 < var2; var10000 = var5) {
			double var3;
			if ((var3 = this.aDoubleArray1347[this.anInt1346] - this.aDoubleArray1347[this.anInt1339]) < 0.0D) {
				++var3;
			}

			this.aDoubleArray1347[this.anInt1346] = var3;
			if (--this.anInt1346 < 0) {
				this.anInt1346 = 96;
			}

			if (--this.anInt1339 < 0) {
				this.anInt1339 = 96;
			}

			this.aDouble1341 -= this.aDouble1345;
			if (this.aDouble1341 < 0.0D) {
				this.aDouble1341 += this.aDouble1343;
			}

			if ((var3 -= this.aDouble1341) < 0.0D) {
				++var3;
			}

			var1[var5] = var3;
			++var5;
		}

	}

	public Class13_Sub1_Sub1(long var1) {
		this.method368((int) Math.abs(var1 % anInt1342));
	}

	@Override
	public final double method359() {
		double var1;
		if ((var1 = this.aDoubleArray1347[this.anInt1346] - this.aDoubleArray1347[this.anInt1339]) < 0.0D) {
			++var1;
		}

		this.aDoubleArray1347[this.anInt1346] = var1;
		if (--this.anInt1346 < 0) {
			this.anInt1346 = 96;
		}

		if (--this.anInt1339 < 0) {
			this.anInt1339 = 96;
		}

		this.aDouble1341 -= this.aDouble1345;
		if (this.aDouble1341 < 0.0D) {
			this.aDouble1341 += this.aDouble1343;
		}

		if ((var1 -= this.aDouble1341) < 0.0D) {
			++var1;
		}

		return var1;
	}
}
