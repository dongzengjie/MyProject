package com.nizubuzu.androidService.enums;

public enum HouseState {
	OFFLINE(-1, "非法房源"), SUCCESS(0, "操作成功"), PASS(2, "通过认证"), INNER_ERROR(
			-1001, "操作失败"),EMPTY(-1002, "房源为空");

	private int state;

	private String stateInfo;

	private HouseState(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static HouseState stateOf(int index) {
		for (HouseState state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
