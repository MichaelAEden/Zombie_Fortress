package animation.entitiesLiving;

import java.awt.Graphics2D;

import tileMap.TileMap;
import entities.living.EntityLiving;

public class AnimationEntityLiving
{
	protected EntityLiving entity;
	protected TileMap tileMap;
	
	protected AnimationBodyPart leftLeg;
	protected AnimationBodyPart rightLeg;
	protected AnimationBodyPart leftArm;
	protected AnimationBodyPart rightArm;
	protected AnimationBodyPart head;
	protected AnimationBodyPart torso;
	
	protected boolean isWalking;

	public AnimationEntityLiving(TileMap tileMap, EntityLiving entity) 
	{
		this.tileMap = tileMap;
		this.entity = entity;
		
		//leftArm = new AnimationBodyPart(tileMap, entity, "Arm", entity.getWidth() / 2, entity.getHeight() / 4, 0, 0, entity.getHeight() / 6, entity.getWidth() / 6);                
		//rightArm = new AnimationBodyPart(tileMap, entity, "Arm", entity.getWidth() / 2, entity.getHeight() / 4, 0, 0, entity.getHeight() / 6, entity.getWidth() / 6);
		/*leftArm = new AnimationBodyPart(tileMap, entity, "Arm", entity.getHeight() / 6, entity.getWidth() / 6);
		leftArm.setJointOnEntity(entity.getHeight() / 2, entity.getHeight() / 4);
		leftArm.setJointOnLimb(0, 0);
		rightArm = new AnimationBodyPart(tileMap, entity, "Arm", entity.getHeight() / 6, entity.getWidth() / 6);
		rightArm.setJointOnEntity(entity.getHeight() / 2, entity.getHeight() / 4);
		rightArm.setJointOnLimb(0, 0);
		torso = new AnimationBodyPart(tileMap, entity, "Torso", entity.getHeight() / 6, entity.getWidth() / 6);
		torso.setJointOnEntity(entity.getHeight() / 2, entity.getHeight() / 4);
		torso.setJointOnLimb(0, 0);*/
		leftArm = new AnimationBodyPart(tileMap, entity, "Arm", entity.getHeight() / 6, entity.getWidth() / 6);
		leftArm.setJointOnEntity(entity.getHeight() / 6, 0);
		leftArm.setJointOnLimb(0, 0);
		rightArm = new AnimationBodyPart(tileMap, entity, "Arm", entity.getHeight() / 6, entity.getWidth() / 6);
		rightArm.setJointOnEntity(0, 0);
		rightArm.setJointOnLimb(0, 0);
		torso = new AnimationBodyPart(tileMap, entity, "Torso", entity.getHeight() / 2, entity.getWidth() / 2);
		torso.setJointOnEntity(0, 0);
		torso.setJointOnLimb(0, 0);
	}
	
	public void draw(Graphics2D g) 
	{
		//leftLeg.draw(g);
		//rightLeg.draw(g);
		/*leftArm.draw(g);
		rightArm.draw(g);
		//head.draw(g);
		torso.draw(g);*/
	}
	
	public void update() 
	{
		if(isWalking)
		{
			leftArm.addRotation(0.01);
			rightArm.addRotation(-0.01);
		}
	}
	
	public void setWalking(boolean isWalking) 
	{
		this.isWalking = isWalking;
	}

}
