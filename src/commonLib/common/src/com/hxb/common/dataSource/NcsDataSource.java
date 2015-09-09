package com.hxb.common.dataSource;

public abstract interface NcsDataSource {
	public abstract NcsConnection getNcsConnetion(long paramLong);

	public abstract NcsConnection getNcsConnetion();
}