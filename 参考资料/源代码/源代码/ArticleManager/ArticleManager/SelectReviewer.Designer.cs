namespace ArticleManager
{
    partial class SelectReviewer
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
            this.button1 = new System.Windows.Forms.Button();
            this.UpdateAuthor_button = new System.Windows.Forms.Button();
            this.dataGridView1 = new System.Windows.Forms.DataGridView();
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).BeginInit();
            this.SuspendLayout();
            // 
            // button1
            // 
            this.button1.Location = new System.Drawing.Point(478, 335);
            this.button1.Name = "button1";
            this.button1.Size = new System.Drawing.Size(116, 34);
            this.button1.TabIndex = 7;
            this.button1.Text = "取消";
            this.button1.UseVisualStyleBackColor = true;
            this.button1.Click += new System.EventHandler(this.button1_Click);
            // 
            // UpdateAuthor_button
            // 
            this.UpdateAuthor_button.Location = new System.Drawing.Point(478, 186);
            this.UpdateAuthor_button.Name = "UpdateAuthor_button";
            this.UpdateAuthor_button.Size = new System.Drawing.Size(116, 34);
            this.UpdateAuthor_button.TabIndex = 6;
            this.UpdateAuthor_button.Text = "确定";
            this.UpdateAuthor_button.UseVisualStyleBackColor = true;
            this.UpdateAuthor_button.Click += new System.EventHandler(this.UpdateAuthor_button_Click);
            // 
            // dataGridView1
            // 
            this.dataGridView1.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dataGridView1.Location = new System.Drawing.Point(12, 12);
            this.dataGridView1.Name = "dataGridView1";
            this.dataGridView1.RowTemplate.Height = 27;
            this.dataGridView1.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dataGridView1.Size = new System.Drawing.Size(460, 529);
            this.dataGridView1.TabIndex = 5;
            // 
            // SelectReviewer
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(8F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(606, 551);
            this.Controls.Add(this.button1);
            this.Controls.Add(this.UpdateAuthor_button);
            this.Controls.Add(this.dataGridView1);
            this.Name = "SelectReviewer";
            this.Text = "选择审稿人";
            this.Load += new System.EventHandler(this.SelectReviewer_Load);
            ((System.ComponentModel.ISupportInitialize)(this.dataGridView1)).EndInit();
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Button button1;
        private System.Windows.Forms.Button UpdateAuthor_button;
        private System.Windows.Forms.DataGridView dataGridView1;
    }
}