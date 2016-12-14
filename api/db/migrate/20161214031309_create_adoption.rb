class CreateAdoption < ActiveRecord::Migration[5.0]
  def change
    create_table :adoptions do |t|
      t.references :old_owner, index: true
      t.references :new_owner, index: true
      t.references :animal, index: true

      t.timestamps null: false
    end
  end
end
