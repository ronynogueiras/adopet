class CreateAnimals < ActiveRecord::Migration[5.0]
  def change
    create_table :animals do |t|
      t.string :name, index: true
      t.string :type, index: true
      t.references :user, index: true

      t.timestamps
    end
  end
end
