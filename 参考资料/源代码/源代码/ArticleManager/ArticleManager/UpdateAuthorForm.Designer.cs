namespace ArticleManager
{
    partial class UpdateAuthorForm
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            this.UpdateAuthor_button = new System.Windows.Forms.Button();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.SuspendLayout();
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(12, 12);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowTemplate.Height = 27;
            this.dataGridView1.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dataGridView1.Size = new System.Drawing.Size(460, 529);
            this.dataGridView1.TabIndex = 0;
            // 
            // UpdateAuthor_button
            // 
            this.UpdateAuthor_button.Location = new System.Drawing.Point(478, 247);
            this.UpdateAuthor_button.Name = "UpdateAuthor_button";
            this.UpdateAuthor_button.Size = new System.Drawing.Size(116, 34);
            this.UpdateAuthor_button.TabIndex = 1;
            this.UpdateAuthor_button.Text = "更新当前作者";
            this.UpdateAuthor_button.UseVisualStyleBackColor = true;
            this.UpdateAuthor_button.Click += new System.EventHandler(this.button1_Click);
            // 
            // UpdateAuthorForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(607, 553);
            this.Controls.Add(this.UpdateAuthor_button);
            this.Controls.Add(this.dataGridView1);
            this.Name = "UpdateAuthorForm";
            this.Text = "更新作者";
            this.Load += new System.EventHandler(this.UpdateAuthorForm_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.DataGridView dataGridView1;
        private System.Windows.Forms.Button UpdateAuthor_button;
    }
}