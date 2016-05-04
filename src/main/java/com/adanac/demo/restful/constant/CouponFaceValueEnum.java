package com.adanac.demo.restful.constant;

/**
 * 
 * 券面值枚举值
 * @author zhangs
 *
 */
public enum CouponFaceValueEnum {

	//1:5 2:10 3:50 4:80 5:100 6:150 7:200 8:300 9:500 10:1000
	FACE_5(1,5),
	FACE_10(2,10),
	FACE_50(3,50),
	FACE_80(4,80),
	FACE_100(5,100),
	FACE_150(6,150),
	FACE_200(7,200),
	FACE_300(8,300),
	FACE_500(9,500),
	FACE_1000(10,1000);
	
	private int value;
	private int desc;
	private CouponFaceValueEnum(int value,int desc)
	{
		this.value=value;
		this.desc=desc;
	}
	public int getDesc() {
		return desc;
	}

	public void setDesc(int desc) {
		this.desc = desc;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
	
	public static int valueOf(int value) {  
        switch (value) {
        case 1:
            return FACE_5.getDesc();
        case 2:
            return FACE_10.getDesc();
        case 3:
            return FACE_50.getDesc();
        case 4:
            return FACE_80.getDesc();
        case 5:
            return FACE_100.getDesc();
        case 6:
            return FACE_150.getDesc();
        case 7:
            return FACE_200.getDesc();
        case 8:
            return FACE_300.getDesc();
        case 9:
            return FACE_500.getDesc();
        case 10:
            return FACE_1000.getDesc();
        default:
            return FACE_5.getDesc();
        }
    }
	
	
}
